package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.RestaurantDto;
import kz.pelmen_delivery.model.dto.RestaurantSimpleDto;
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
    public ResponseEntity<List<RestaurantSimpleDto>> getRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantInfo(
            @PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantInfo(id));
    }

    @GetMapping(value = "search",params = "name")
    public ResponseEntity<RestaurantDto> findByName(@RequestParam String name) {
        return ResponseEntity.ok(restaurantService.findByName(name));
    }
}
