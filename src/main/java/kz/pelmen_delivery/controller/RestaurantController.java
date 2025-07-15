package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.RestaurantDto;
import kz.pelmen_delivery.model.request.RestaurantRequest;
import kz.pelmen_delivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @PostMapping
    public ResponseEntity<Void> createRestaurant(
            @RequestBody @Valid RestaurantRequest request) {
        restaurantService.createRestaurant(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantInfo(
            @PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantInfo(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(
            @PathVariable Long id,
            @RequestBody @Valid RestaurantRequest request) {
        restaurantService.updateRestaurant(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(
            @PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }
}
