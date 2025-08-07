package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.RegionDto;
import kz.pelmen_delivery.model.request.RegionRequest;
import kz.pelmen_delivery.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDto>> findAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> createRegion(
            @RequestBody @Valid RegionRequest request) {
        regionService.createRegion(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDto> findRegionById(
            @PathVariable Long id) {
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateRegion(
            @PathVariable Long id,
            @RequestBody @Valid RegionRequest request) {
        regionService.updateRegion(id, request);
        return ResponseEntity.ok().build();
    }
}
