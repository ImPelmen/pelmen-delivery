package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.RoleNotFoundException;
import kz.pelmen_delivery.exception.UserAlreadyExists;
import kz.pelmen_delivery.exception.UserNotFoundException;
import kz.pelmen_delivery.model.entity.DomainUser;
import kz.pelmen_delivery.model.entity.Role;
import kz.pelmen_delivery.model.enums.RoleTitle;
import kz.pelmen_delivery.model.request.AuthRequest;
import kz.pelmen_delivery.model.request.RegisterUserRequest;
import kz.pelmen_delivery.model.response.AuthResponse;
import kz.pelmen_delivery.repository.DomainUserRepository;
import kz.pelmen_delivery.repository.RoleRepository;
import kz.pelmen_delivery.service.AuthService;
import kz.pelmen_delivery.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final DomainUserRepository domainUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private final Set<String> tokenBlackList = new HashSet<>();

    @Override
    public AuthResponse register(RegisterUserRequest request) {
        String email = request.getEmail();
        if (emailExists(email)) {
            log.error("User with email {} is already exists", email);
            throw new UserAlreadyExists(String.format("Пользователь с email %s уже существует!", email));
        }
        Role role = roleRepository.findByName(RoleTitle.CLIENT)
                .orElseThrow(
                        () -> new RoleNotFoundException(String.format("Роль %s не найдена!", RoleTitle.CLIENT)));
        DomainUser user = DomainUser.builder()
                .surname(request.getSurname())
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(role))
                .build();

        user = domainUserRepository.save(user);
        String jwt = prepareJwt(user);

        return AuthResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    public void logout(String authHeader) {
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7).trim();
        jwtUtil.blackListToken(token);
    }

    private boolean emailExists(String email) {
        return domainUserRepository
                .findByEmail(email)
                .isPresent();
    }

    @Override
    public AuthResponse auth(AuthRequest request) {
        String email = request.getEmail();
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        email, request.getPassword()));

        DomainUser user = domainUserRepository.findByEmail(email)
                .orElseThrow(
                        () -> {
                            log.error("User with email {} is not found!", email);
                            return new UserNotFoundException(String.format("Пользователь с email %s не найден!", email));
                        });
        String jwt = prepareJwt(user);
        return AuthResponse.builder()
                .token(jwt)
                .build();
    }

    private String prepareJwt(DomainUser user) {
        List<SimpleGrantedAuthority> roles = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .toList();
        return jwtUtil.generateToken(
                new User(
                        user.getEmail(),
                        user.getPassword(),
                        roles
                ));
    }


}
