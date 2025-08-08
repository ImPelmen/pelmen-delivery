package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.DomainUserDto;
import kz.pelmen_delivery.model.request.DomainUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface DomainUserService extends UserDetailsService {

    List<DomainUserDto> findAll();

    void updateDomainUserById(Long id, DomainUserRequest request);

    void deleteUser(Long id);

    DomainUserDto getInfoById(Long id);

    DomainUserDto getInfoByEmail(String email);

    void updateDomainUserByEmail(String email, DomainUserRequest request);

    void deleteUserByEmail(String email);
}
