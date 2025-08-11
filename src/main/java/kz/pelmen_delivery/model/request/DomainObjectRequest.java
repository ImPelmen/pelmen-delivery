package kz.pelmen_delivery.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainObjectRequest {

    private String address;

    private boolean isApartment;

    private Long regionId;
}
