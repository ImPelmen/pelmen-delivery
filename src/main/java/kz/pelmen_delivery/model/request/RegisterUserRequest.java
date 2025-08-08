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
public class RegisterUserRequest {

    private String surname;

    @NotBlank(message = "Имя пользователя обязательно для заполнения!")
    private String name;

    @NotBlank(message = "Номер телефона пользователя обязательно для заполнения")
    private String phoneNumber;

    @NotBlank(message = "Email пользователя обязательно для заполнения!")
    private String email;

    @NotBlank(message = "Пароль пользователя обязательно для заполнения!")
    private String password;
}
