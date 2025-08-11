package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.DomainObjectDto;
import kz.pelmen_delivery.model.request.DomainObjectRequest;
import kz.pelmen_delivery.service.DomainObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/domain-object")
public class DomainObjectController {

    private static final String USERNAME_HEADER = "X-User-Username";

    private final DomainObjectService domainObjectService;

    @GetMapping
    public ResponseEntity<List<DomainObjectDto>> findAll() {
        return ResponseEntity.ok(domainObjectService.findAll());
    }

    @GetMapping("/mine")
    public ResponseEntity<List<DomainObjectDto>> findAllUsers(
            @RequestHeader(USERNAME_HEADER) String email) {
        return ResponseEntity.ok(domainObjectService.findAllUsers(email));
    }

    @PostMapping
    public ResponseEntity<Void> createDomainObject(
            @RequestHeader(USERNAME_HEADER) String email,
            @RequestBody @Valid DomainObjectRequest request) {
        domainObjectService.createDomainObject(email, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomainObjectDto> findById(
            @PathVariable Long id) {
        return ResponseEntity.ok(domainObjectService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateDomainObject(
            @PathVariable Long id,
            @RequestHeader(USERNAME_HEADER) String email,
            @RequestBody DomainObjectRequest request) {
        domainObjectService.updateDomainObject(email, id, request);
        return ResponseEntity.ok().build();
    }
}
