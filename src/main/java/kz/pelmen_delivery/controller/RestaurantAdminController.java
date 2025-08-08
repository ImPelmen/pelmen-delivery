package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.DomainUserDto;
import kz.pelmen_delivery.model.dto.RestaurantUserDto;
import kz.pelmen_delivery.model.request.EmployeeRequest;
import kz.pelmen_delivery.model.request.RestaurantRequest;
import kz.pelmen_delivery.service.DomainUserService;
import kz.pelmen_delivery.service.RestaurantEmployeeService;
import kz.pelmen_delivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant-admin")
public class RestaurantAdminController {

    private final RestaurantService restaurantService;

    private final DomainUserService domainUserService;

    private final RestaurantEmployeeService restaurantEmployeeService;

    @GetMapping("/{id}")
    public ResponseEntity<List<DomainUserDto>> getRestaurantUsers() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<RestaurantUserDto>> getRestaurantEmployees() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restaurant/{id}/employee")
    public ResponseEntity<Void> addEmployeeByEmail(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeRequest request) {
        restaurantEmployeeService.addEmployee(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/restaurant/{id}/employee")
    public ResponseEntity<Void> deleteEmployeeByEmail(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeRequest request) {
        restaurantEmployeeService.removeEmployee(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/restaurant/{id}")
    public ResponseEntity<Void> updateRestaurant(
            @PathVariable Long id,
            @RequestBody @Valid RestaurantRequest request) {
        restaurantService.updateRestaurant(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<DomainUserDto> getUserInfoById(
            @PathVariable Long id) {
        return ResponseEntity.ok(domainUserService.getInfoById(id));
    }
}
