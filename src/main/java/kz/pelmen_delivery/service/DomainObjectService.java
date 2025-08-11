package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.DomainObjectDto;
import kz.pelmen_delivery.model.request.DomainObjectRequest;

import java.util.List;

public interface DomainObjectService {

    List<DomainObjectDto> findAll();

    List<DomainObjectDto> findAllUsers(String email);

    void createDomainObject(String email, DomainObjectRequest request);

    DomainObjectDto findById(Long id);

    void updateDomainObject(String email, Long id, DomainObjectRequest request);
}
