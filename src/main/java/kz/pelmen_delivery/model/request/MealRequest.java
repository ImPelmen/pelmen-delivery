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

    @NotBlank(message = "Название блюда не может быть пустым!")
    private String name;

    @NotBlank(message = "Описание блюда не может быть пустым!")
    private String description;

    @NotNull(message = "Цена блюда не может быть пустой!")
    private Long price;

    @NotNull(message = "Не передан номер категории блюда!")
    private Long mealCategoryId;
}
