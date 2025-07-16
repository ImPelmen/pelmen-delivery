package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.MealCategoryDto;
import kz.pelmen_delivery.model.dto.MealDto;
import kz.pelmen_delivery.model.request.MealCategoryRequest;
import kz.pelmen_delivery.model.request.MealRequest;
import kz.pelmen_delivery.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealDto>> getAll() {
        return ResponseEntity.ok(mealService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> createMeal(
            @RequestBody @Valid MealRequest request) {
        mealService.createMeal(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> findMealById(
            @PathVariable Long id) {
        return ResponseEntity.ok(mealService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMeal(
            @PathVariable Long id,
            @RequestBody @Valid MealRequest request) {
        mealService.updateMeal(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(
            @PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.ok().build();
    }

}
