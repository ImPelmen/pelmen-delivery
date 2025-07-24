package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.MealCategoryNotFoundException;
import kz.pelmen_delivery.exception.RestaurantNotFoundException;
import kz.pelmen_delivery.model.dto.MealCategoryDto;
import kz.pelmen_delivery.model.entity.MealCategory;
import kz.pelmen_delivery.model.entity.Restaurant;
import kz.pelmen_delivery.model.request.MealCategoryRequest;
import kz.pelmen_delivery.repository.MealCategoryRepository;
import kz.pelmen_delivery.repository.RestaurantRepository;
import kz.pelmen_delivery.service.MealCategoryService;
import kz.pelmen_delivery.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealCategoryServiceImpl implements MealCategoryService {

    private final MealCategoryRepository categoryRepository;

    private final RestaurantRepository restaurantRepository;

    @Override
    public void createCategory(MealCategoryRequest request) {
        Long restaurantId = request.getRestaurantId();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            log.error("Restaurant with id {} is not found!", restaurantId);
            throw new RestaurantNotFoundException(String.format("Ресторан с номером %s не найден!", restaurantId));
        }
        Restaurant restaurant = restaurantOptional.get();

        MealCategory mealCategory = MealCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .restaurant(restaurant)
                .build();

        categoryRepository.save(mealCategory);
    }

    @Override
    public List<MealCategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(mealCategory -> ModelMapperUtil.map(mealCategory, MealCategoryDto.class))
                .toList();
    }

    @Override
    public MealCategoryDto findById(Long id) {
        MealCategory mealCategory = findMealCategoryById(id);
        return ModelMapperUtil.map(mealCategory, MealCategoryDto.class);
    }

    @Override
    public void updateCategory(Long id, MealCategoryRequest request) {
        MealCategory mealCategory = findMealCategoryById(id);
        mealCategory.setName(request.getName());
        mealCategory.setDescription(request.getDescription());
        categoryRepository.save(mealCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        MealCategory mealCategory = findMealCategoryById(id);
        categoryRepository.delete(mealCategory);
    }

    private MealCategory findMealCategoryById(Long id) {
        Optional<MealCategory> mealCategoryOptional = categoryRepository.findById(id);
        if (mealCategoryOptional.isEmpty()) {
            log.error("Meal category with id {} is not found", id);
            throw new MealCategoryNotFoundException(String.format("Категория еды с номером %s не найдена!", id));
        }
        return mealCategoryOptional.get();
    }
}
