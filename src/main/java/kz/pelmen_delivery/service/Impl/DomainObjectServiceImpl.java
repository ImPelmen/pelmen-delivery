package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.RegionNotFoundException;
import kz.pelmen_delivery.exception.UserNotFoundException;
import kz.pelmen_delivery.model.dto.DomainObjectDto;
import kz.pelmen_delivery.model.entity.DomainObject;
import kz.pelmen_delivery.model.entity.DomainUser;
import kz.pelmen_delivery.model.entity.Region;
import kz.pelmen_delivery.model.request.DomainObjectRequest;
import kz.pelmen_delivery.repository.DomainObjectRepository;
import kz.pelmen_delivery.repository.DomainUserRepository;
import kz.pelmen_delivery.repository.RegionRepository;
import kz.pelmen_delivery.service.DomainObjectService;
import kz.pelmen_delivery.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainObjectServiceImpl implements DomainObjectService {

    private final DomainObjectRepository domainObjectRepository;

    private final DomainUserRepository domainUserRepository;

    private final RegionRepository regionRepository;

    @Override
    public List<DomainObjectDto> findAll() {
        return domainObjectRepository.findAll()
                .stream()
                .map(domainObject -> ModelMapperUtil.map(domainObject, DomainObjectDto.class))
                .toList();
    }

    @Override
    public List<DomainObjectDto> findAllUsers(String email) {
        DomainUser domainUser = domainUserRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User with email {} is not found, while creating domain object", email);
            return new UserNotFoundException(String.format("Пользователь с email %s не найден!", email));
        });

        return domainObjectRepository.findAllByUser(domainUser)
                .stream()
                .map(domainObject -> ModelMapperUtil.map(domainObject, DomainObjectDto.class))
                .toList();
    }

    @Override
    public void createDomainObject(String email, DomainObjectRequest request) {
        Long regionId = request.getRegionId();
        DomainUser domainUser = domainUserRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User with email {} is not found, while creating domain object", email);
            return new UserNotFoundException(String.format("Пользователь с email %s не найден!", email));
        });
        Region region = regionRepository.findById(regionId).orElseThrow(() -> {
            log.error("Region with id {} is not found while creating domain object", regionId);
            return new RegionNotFoundException(String.format("Регион с номером %s не найден!", regionId));
        });
        DomainObject domainObject = DomainObject.builder()
                .address(request.getAddress())
                .isApartment(request.isApartment())
                .region(region)
                .user(domainUser)
                .build();
        domainObjectRepository.save(domainObject);
    }

    @Override
    public DomainObjectDto findById(Long id) {
        return domainObjectRepository.findById(id)
                .map(domainObject -> ModelMapperUtil.map(domainObject, DomainObjectDto.class))
                .orElseThrow(() -> {
                    log.error("Domain object with id {} is not found", id);
                    return new RegionNotFoundException(String.format("Регион с номером %s не найден!", id));
                });
    }

    @Override
    public void updateDomainObject(String email, Long id, DomainObjectRequest request) {
        Long regionId = request.getRegionId();
        DomainUser domainUser = domainUserRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User with email {} is not found, while creating domain object", email);
            return new UserNotFoundException(String.format("Пользователь с email %s не найден!", email));
        });
        Region region = regionRepository.findById(regionId).orElseThrow(() -> {
            log.error("Region with id {} is not found while creating domain object", regionId);
            return new RegionNotFoundException(String.format("Регион с номером %s не найден!", regionId));
        });

        DomainObject domainObject = domainObjectRepository.findById(id).orElseThrow(() -> {
            log.error("Domain object with id {} is not found", id);
            return new RegionNotFoundException(String.format("Регион с номером %s не найден!", id));
        });

        domainObject.setAddress(request.getAddress());
        domainObject.setApartment(request.isApartment());
        domainObject.setRegion(region);
        domainObject.setUser(domainUser);
        domainObjectRepository.save(domainObject);
    }
}
