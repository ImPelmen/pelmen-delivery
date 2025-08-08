package kz.pelmen_delivery.model.request;

import jakarta.validation.constraints.NotBlank;
import kz.pelmen_delivery.model.enums.RoleTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainUserRequest {

    private String surname;

    @NotBlank(message = "Имя пользователя обязательно для заполнения!")
    private String name;

    @NotBlank(message = "Номер телефона пользователя обязательно для заполнения")
    private String phoneNumber;

    @NotBlank(message = "Email пользователя обязательно для заполнения!")
    private String email;

    private List<RoleTitle> roles;
}
