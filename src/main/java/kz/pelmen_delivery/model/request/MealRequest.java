package kz.pelmen_delivery.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealRequest {

    @NotNull
    private Long restaurantId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private Long price;

    @NotBlank
    private Long mealCategoryId;
}
