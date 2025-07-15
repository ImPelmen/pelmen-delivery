package kz.pelmen_delivery.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealCategoryRequest {

    @NotBlank(message = "Имя категории не должно быть пустым")
    private String name;

    private String description;
}
