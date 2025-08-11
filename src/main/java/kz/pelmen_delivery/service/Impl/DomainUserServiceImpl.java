package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.AlreadyAssignedException;
import kz.pelmen_delivery.exception.RoleNotFoundException;
import kz.pelmen_delivery.exception.UserNotFoundException;
import kz.pelmen_delivery.model.dto.DomainUserDto;
import kz.pelmen_delivery.model.entity.DomainUser;
import kz.pelmen_delivery.model.entity.Role;
import kz.pelmen_delivery.model.enums.RoleTitle;
import kz.pelmen_delivery.model.request.AddRoleRequest;
import kz.pelmen_delivery.model.request.DomainUserRequest;
import kz.pelmen_delivery.repository.DomainUserRepository;
import kz.pelmen_delivery.repository.RoleRepository;
import kz.pelmen_delivery.service.DomainUserService;
import kz.pelmen_delivery.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainUserServiceImpl implements DomainUserService {

    private final DomainUserRepository domainUserRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Здесь username = email;
        DomainUser user = domainUserRepository.findByEmail(username)
                .orElseThrow(
                        () -> {
                            log.error("User with email {} is not found!", username);
                            return new UserNotFoundException(String.format("Пользователь с email %s не найден!", username));
                        });
        List<SimpleGrantedAuthority> roles = user.getRoles().stream().map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .toList();
        return new User(
                user.getEmail(),
                user.getPassword(),
                roles
        );
    }

    @Override
    public List<DomainUserDto> findAll() {
        return domainUserRepository.findAll()
                .stream()
                .map(user -> ModelMapperUtil.map(user, DomainUserDto.class))
                .toList();
    }

    @Override
    public void updateDomainUserById(Long id, DomainUserRequest request) {
        DomainUser user = findUserById(id);
        updateUser(request, user);
    }


    private void updateUser(DomainUserRequest request, DomainUser user) {
        try {
            Set<Role> newRoles = request.getRoles().stream().map(role ->
                    roleRepository.findByName(role).orElseThrow(() -> {
                        log.error("Role with RoleTitle name {} is not found", role.getName());
                        return new RoleNotFoundException(String.format("Роль с названием %s не найдена", role.getName()));
                    })).collect(Collectors.toSet());
            newRoles.addAll(user.getRoles());
            user.setRoles(newRoles.stream().toList());
            user.setName(request.getName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setEmail(request.getEmail());
            user.setSurname(request.getSurname());
            domainUserRepository.save(user);
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage(), e);
            if (e instanceof InvocationTargetException) {
                log.error("Caused by: {}", e.getCause(), e.getCause());
            }
            throw e;
        }
    }

    @Override
    public void addRole(Long id, AddRoleRequest request) {
        RoleTitle roleTitle = request.getRoleTitle();
        DomainUser user = domainUserRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("Пользователь с номером %s не найден!", id)));
        Role role = roleRepository.findByName(roleTitle)
                .orElseThrow(() ->
                        new RoleNotFoundException(String.format("Роль с именем %s не найдена!", roleTitle)));
        if (user.getRoles().stream().anyMatch(r -> r.getName().equals(role.getName()))) {
            log.warn("Role {} already assigned to user {}", role.getName(), id);
            throw new AlreadyAssignedException("У пользователя уже существует роль!");
        }
        user.getRoles().add(role);
        domainUserRepository.save(user);
    }

    private DomainUser findUserById(Long id) {
        return domainUserRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {} is not found", id);
            return new UserNotFoundException(String.format("Пользователь с номером %s не найден!", id));
        });
    }

    @Override
    public void deleteUser(Long id) {
        DomainUser user = findUserById(id);
        user.setDeleted(true);
        domainUserRepository.save(user);
    }

    @Override
    public DomainUserDto getInfoById(Long id) {
        return domainUserRepository.findById(id)
                .map(user -> ModelMapperUtil.map(user, DomainUserDto.class))
                .orElseThrow(() -> {
                    log.error("User with id {} is not found while updating", id);
                    return new UserNotFoundException(String.format("Пользователь с номером %s не найден!", id));
                });
    }

    @Override
    public DomainUserDto getInfoByEmail(String email) {
        return domainUserRepository.findByEmail(email)
                .map(user -> ModelMapperUtil.map(user, DomainUserDto.class))
                .orElseThrow(() -> {
                    log.error("User with email {} is not found while updating", email);
                    return new UserNotFoundException(String.format("Пользователь с email %s не найден!", email));
                });
    }

    @Override
    public void updateDomainUserByEmail(String email, DomainUserRequest request) {
        DomainUser domainUser = domainUserRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} is not found while updating", email);
                    return new UserNotFoundException(String.format("Пользователь с email %s не найден!", email));
                });
        updateUser(request, domainUser);
    }

    @Override
    public void deleteUserByEmail(String email) {
        DomainUser domainUser = domainUserRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} is not found while updating", email);
                    return new UserNotFoundException(String.format("Пользователь с email %s не найден!", email));
                });
        domainUser.setDeleted(true);
        domainUserRepository.save(domainUser);
    }
}
