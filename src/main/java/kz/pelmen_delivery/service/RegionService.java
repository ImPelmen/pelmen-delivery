package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.RegionDto;
import kz.pelmen_delivery.model.entity.Region;
import kz.pelmen_delivery.model.request.RegionRequest;

import java.util.List;

public interface RegionService {

    List<RegionDto> getAll();

    void createRegion(RegionRequest request);

    RegionDto findById(Long id);

    void updateRegion(Long id, RegionRequest request);

    Region findRegionById(Long id);
}
