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
public class ChangeOrderStatusRequest {

    @NotBlank(message = "Статус заказа не может быть пустым!")
    private String statusTitle;

    private Long objectId;
}
