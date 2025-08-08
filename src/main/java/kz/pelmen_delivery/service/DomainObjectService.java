package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.DomainObjectDto;
import kz.pelmen_delivery.model.request.DomainObjectRequest;

import java.util.List;

public interface DomainObjectService {

    List<DomainObjectDto> findAll();

    void createDomainObject(DomainObjectRequest request);

    DomainObjectDto findById(Long id);

    void updateDomainObject(Long id, DomainObjectRequest request);
}
