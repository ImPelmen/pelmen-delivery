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
public class RestaurantRequest {

    @NotBlank(message = "Название ресторана не может быть пустым")
    private String name;

    @NotBlank(message = "Поле адреса не может быть пустой")
    private String address;

    private String description;
}
