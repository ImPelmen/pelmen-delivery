package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.MealDto;
import kz.pelmen_delivery.model.request.MealRequest;

import java.util.List;

public interface MealService {

    List<MealDto> getAll();

    void createMeal(MealRequest request);

    MealDto findById(Long id);

    void updateMeal(Long id, MealRequest request);

    void deleteMeal(Long id);
}
