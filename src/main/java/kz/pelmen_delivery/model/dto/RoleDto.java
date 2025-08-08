package kz.pelmen_delivery.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import kz.pelmen_delivery.model.enums.RoleTitle;
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
public class RoleDto {

    private Long id;

    private RoleTitle name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
