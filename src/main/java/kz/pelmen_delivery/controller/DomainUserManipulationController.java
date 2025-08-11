package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.DomainOrderDto;
import kz.pelmen_delivery.model.dto.DomainUserDto;
import kz.pelmen_delivery.model.request.DomainUserRequest;
import kz.pelmen_delivery.service.DomainUserService;
import kz.pelmen_delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class DomainUserManipulationController {

    private static final String USERNAME_HEADER = "X-User-Username";

    private final DomainUserService domainUserService;

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<DomainUserDto> getInfoByEmail(
            @RequestHeader(USERNAME_HEADER) String email) {
        return ResponseEntity.ok(domainUserService.getInfoByEmail(email));
    }

    @PatchMapping
    public ResponseEntity<Void> updateUserByEmail(
            @RequestHeader(USERNAME_HEADER) String email,
            @RequestBody @Valid DomainUserRequest request) {
        domainUserService.updateDomainUserByEmail(email, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserByEmail(
            @RequestHeader(USERNAME_HEADER) String email) {
        domainUserService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deliveries")
    public ResponseEntity<List<DomainOrderDto>> getAllDeliveries(
            @RequestHeader(USERNAME_HEADER) String email) {
        return ResponseEntity.ok(orderService.getCourierAllOrders(email));
    }

    @GetMapping("/deliveries/closed")
    public ResponseEntity<List<DomainOrderDto>> getAllClosedDeliveries(
            @RequestHeader(USERNAME_HEADER) String email) {
        return ResponseEntity.ok(orderService.getCourierOrderHistory(email));
    }
}
