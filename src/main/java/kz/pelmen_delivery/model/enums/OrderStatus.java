package kz.pelmen_delivery.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    CREATED("Создано"),
    OPENED("Открыто"),
    IN_WORK("В работе"),
    WAITING_TO_PICKUP("Ожидает доставки"),
    DELIVERING("Доставляется"),
    DELIVERED("Доставлен"),
    CANCELED("Отменен");

    private final String title;

    public static OrderStatus findByTitle(String title) {
        for (OrderStatus status :
             values()) {
            if (status.getTitle().equals(title)) {
                return status;
            }
        }

        return null;
    }

    public static Set<OrderStatus> getActiveStatuses() {
        return Set.of(CREATED);
    }
}
