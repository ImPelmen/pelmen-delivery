package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.MealNotFoundException;
import kz.pelmen_delivery.exception.RestaurantNotFoundException;
import kz.pelmen_delivery.model.dto.MealDto;
import kz.pelmen_delivery.model.entity.Meal;
import kz.pelmen_delivery.model.entity.MealCategory;
import kz.pelmen_delivery.model.entity.Restaurant;
import kz.pelmen_delivery.model.request.MealRequest;
import kz.pelmen_delivery.repository.MealCategoryRepository;
import kz.pelmen_delivery.repository.MealRepository;
import kz.pelmen_delivery.repository.RestaurantRepository;
import kz.pelmen_delivery.service.MealService;
import kz.pelmen_delivery.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;

    private final MealCategoryRepository categoryRepository;

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<MealDto> getAll() {
        return mealRepository.findAll()
                .stream()
                .map(meal -> ModelMapperUtil.map(meal, MealDto.class))
                .toList();
    }

    @Override
    public void createMeal(MealRequest request) {
        Long categoryId = request.getMealCategoryId();
        Optional<MealCategory> mealCategoryOptional = categoryRepository.findById(categoryId);
        if (mealCategoryOptional.isEmpty()) {
            log.error("Meal category with id {} is not found!", categoryId);
            throw new RestaurantNotFoundException(String.format("Категория с номером %s не найден!", categoryId));
        }
        MealCategory mealCategory = mealCategoryOptional.get();

        Meal meal = Meal.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .mealCategory(mealCategory)
                .build();
        mealRepository.save(meal);
    }

    @Override
    public MealDto findById(Long id) {
        Meal meal = findMealById(id);
        return ModelMapperUtil.map(meal, MealDto.class);
    }

    @Override
    public void updateMeal(Long id, MealRequest request) {
        Meal meal = findMealById(id);
        meal.setName(request.getName());
        meal.setDescription(request.getDescription());
        meal.setPrice(request.getPrice());
        mealRepository.save(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        Meal meal = findMealById(id);
        mealRepository.delete(meal);
    }

    private Meal findMealById(Long id) {
        Optional<Meal> mealOptional = mealRepository.findById(id);
        if (mealOptional.isEmpty()) {
            log.error("Meal with id {} is not found", id);
            throw new MealNotFoundException(String.format("Блюда с номером %s не найдено!", id));
        }

        return mealOptional.get();
    }
}
