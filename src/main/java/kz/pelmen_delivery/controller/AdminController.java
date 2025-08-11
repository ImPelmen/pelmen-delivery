package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.DomainUserDto;
import kz.pelmen_delivery.model.dto.RestaurantSimpleDto;
import kz.pelmen_delivery.model.request.AddRoleRequest;
import kz.pelmen_delivery.model.request.DomainUserRequest;
import kz.pelmen_delivery.model.request.RestaurantRequest;
import kz.pelmen_delivery.service.DomainUserService;
import kz.pelmen_delivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final RestaurantService restaurantService;

    private final DomainUserService domainUserService;

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantSimpleDto>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/users")
    public ResponseEntity<List<DomainUserDto>> getAllUsers() {
        return ResponseEntity.ok(domainUserService.findAll());
    }

    @PostMapping("/restaurant/create")
    public ResponseEntity<Void> createRestaurant(
            @RequestBody @Valid RestaurantRequest request) {
        restaurantService.createRestaurant(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<Void> deleteRestaurant(
            @PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid DomainUserRequest request) {
        domainUserService.updateDomainUserById(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {
        domainUserService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/{id}/add-role")
    public ResponseEntity<Void> addRole(
            @PathVariable Long id,
            @RequestBody @Valid AddRoleRequest request) {
        domainUserService.addRole(id, request);
        return ResponseEntity.ok().build();
    }
}
