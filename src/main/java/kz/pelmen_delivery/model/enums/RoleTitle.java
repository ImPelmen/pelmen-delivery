package kz.pelmen_delivery.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTitle {
    CLIENT("Клиент"),
    RESTAURANT("Ресторан"),
    COURIER("Курьер"),
    ADMIN("Администратор"),
    RESTAURANT_ADMIN("Администратор ресторана");

    private final String name;

    public static RoleTitle findByName(String name) {
        for (RoleTitle roleTitle : values()) {
            if (roleTitle.name().equals(name)) {
                return roleTitle;
            }
        }

        return null;
    }
}
