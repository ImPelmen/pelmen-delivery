package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.MealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealCategoryRepository extends JpaRepository<MealCategory, Long> {
}
