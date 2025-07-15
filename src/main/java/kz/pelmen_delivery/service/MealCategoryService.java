package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.MealCategoryDto;
import kz.pelmen_delivery.model.request.MealCategoryRequest;

import java.util.List;

public interface MealCategoryService {

    void createCategory(MealCategoryRequest request);

    List<MealCategoryDto> getAll();

    MealCategoryDto findById(Long id);

    void updateCategory(Long id, MealCategoryRequest request);

    void deleteCategory(Long id);
}
