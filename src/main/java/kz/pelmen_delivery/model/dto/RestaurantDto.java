package kz.pelmen_delivery.model.dto;

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
public class RestaurantDto {

    private Long id;

    private String name;

    private String address;

    private String description;

    private List<MealDto> meals;

    private List<DomainOrderDto> orders;

    private List<RestaurantUserDto> employees;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
