package kz.pelmen_delivery.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.pelmen_delivery.model.enums.RoleTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleRequest {

    @NotNull(message = "Роль не должна быть пустой!")
    private RoleTitle roleTitle;
}
