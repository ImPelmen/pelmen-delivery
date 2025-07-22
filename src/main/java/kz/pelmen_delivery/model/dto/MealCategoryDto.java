package kz.pelmen_delivery.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealCategoryDto {

    private Long id;

    private String name;

    private String description;

    @JsonBackReference
    private RestaurantDto restaurant;

    private List<MealDto> meals;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
