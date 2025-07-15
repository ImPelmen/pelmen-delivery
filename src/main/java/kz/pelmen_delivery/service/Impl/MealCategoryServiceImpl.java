package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.MealCategoryNotFoundException;
import kz.pelmen_delivery.model.dto.MealCategoryDto;
import kz.pelmen_delivery.model.entity.MealCategory;
import kz.pelmen_delivery.model.request.MealCategoryRequest;
import kz.pelmen_delivery.repository.MealCategoryRepository;
import kz.pelmen_delivery.service.MealCategoryService;
import kz.pelmen_delivery.util.ObjectMapperUtil;
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

    private final ObjectMapperUtil objectMapperUtil;

    @Override
    public void createCategory(MealCategoryRequest request) {
        MealCategory mealCategory = MealCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        categoryRepository.save(mealCategory);
    }

    @Override
    public List<MealCategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(mealCategory -> objectMapperUtil.convert(mealCategory, MealCategoryDto.class))
                .toList();
    }

    @Override
    public MealCategoryDto findById(Long id) {
        MealCategory mealCategory = findMealCategoryById(id);
        return objectMapperUtil.convert(mealCategory, MealCategoryDto.class);
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
