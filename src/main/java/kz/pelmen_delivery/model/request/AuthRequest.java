package kz.pelmen_delivery.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "Email пользователя не должен быть пустым!")
    private String email;

    @NotNull(message = "Пароль пользователя не должен быть пустым!")
    private String password;
}
