package kz.pelmen_delivery.controller;

import jakarta.validation.Valid;
import kz.pelmen_delivery.model.dto.MealCategoryDto;
import kz.pelmen_delivery.model.request.MealCategoryRequest;
import kz.pelmen_delivery.service.MealCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class MealCategoryController {

    private final MealCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<MealCategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @RequestBody @Valid MealCategoryRequest request) {
        categoryService.createCategory(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealCategoryDto> findCategoryById(
        @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid MealCategoryRequest request) {
        categoryService.updateCategory(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
