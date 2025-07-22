package kz.pelmen_delivery.model.dto;

import kz.pelmen_delivery.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainOrderDto {

    private Long id;

    private OrderStatus status;

    private String assignedOn;

    private String createdBy;

    private List<MealDto> meals;

    private RestaurantDto restaurant;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
