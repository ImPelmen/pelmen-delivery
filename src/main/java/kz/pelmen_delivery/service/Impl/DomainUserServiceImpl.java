package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.UserNotFoundException;
import kz.pelmen_delivery.model.entity.DomainUser;
import kz.pelmen_delivery.model.enums.RoleTitle;
import kz.pelmen_delivery.repository.DomainUserRepository;
import kz.pelmen_delivery.service.DomainUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainUserServiceImpl implements DomainUserService {

    private final DomainUserRepository domainUserRepository;

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
}
