package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.RegionNotFoundException;
import kz.pelmen_delivery.model.dto.RegionDto;
import kz.pelmen_delivery.model.entity.Region;
import kz.pelmen_delivery.model.request.RegionRequest;
import kz.pelmen_delivery.repository.RegionRepository;
import kz.pelmen_delivery.service.RegionService;
import kz.pelmen_delivery.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<RegionDto> getAll() {
        return regionRepository.findAll()
                .stream()
                .map(region -> ModelMapperUtil.map(region, RegionDto.class))
                .toList();
    }

    @Override
    public void createRegion(RegionRequest request) {
        Region region = Region.builder()
                .name(request.getName())
                .build();

        regionRepository.save(region);
    }

    @Override
    public RegionDto findById(Long id) {
        Region region = findRegionById(id);
        return ModelMapperUtil.map(region, RegionDto.class);
    }

    @Override
    public void updateRegion(Long id, RegionRequest request) {
        Region region = findRegionById(id);
        region.setName(request.getName());
        regionRepository.save(region);
    }

    @Override
    public Region findRegionById(Long id) {
        Optional<Region> regionOptional = regionRepository.findById(id);
        if (regionOptional.isEmpty()) {
            log.error("Region with id {} is not found", id);
            throw new RegionNotFoundException(String.format("Регион с номером %s не найден!", id));
        }
        return regionOptional.get();
    }
}
