package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.DomainOrderDto;
import kz.pelmen_delivery.model.request.ChangeOrderStatusRequest;
import kz.pelmen_delivery.model.request.OrderRequest;
import kz.pelmen_delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private static final String USERNAME_HEADER = "X-User-Username";

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<DomainOrderDto>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity<DomainOrderDto> handleOrder(
            @RequestBody @Valid OrderRequest request,
            @RequestHeader(USERNAME_HEADER) String email) {
        return ResponseEntity.ok(orderService.handleOrder(request, email));
    }

    @PostMapping(value = "/{id}/clear")
    public ResponseEntity<DomainOrderDto> clearOrder(
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.clearOrder(id));
    }

    @PatchMapping(value = "/{id}/change-status")
    public ResponseEntity<DomainOrderDto> changeOrderStatus(
            @RequestHeader(USERNAME_HEADER) String email,
            @PathVariable Long id,
            @RequestBody @Valid ChangeOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.changeOrderStatus(id, request, email));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DomainOrderDto> getOrderById(
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping(value = "/mine/orders")
    public ResponseEntity<List<DomainOrderDto>> getUserOrders(
            @RequestHeader(USERNAME_HEADER) String email) {
        return ResponseEntity.ok(orderService.getUsersOrder(email));
    }

    @GetMapping(value = "/available")
    public ResponseEntity<List<DomainOrderDto>> getAvailableOrders(
            @RequestHeader(USERNAME_HEADER) String email) {
        return ResponseEntity.ok(orderService.getAvailableOrders(email));
    }
}
