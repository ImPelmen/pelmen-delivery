package kz.pelmen_delivery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {

    private Long id;

    private String name;

    private String description;

    private Long price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
