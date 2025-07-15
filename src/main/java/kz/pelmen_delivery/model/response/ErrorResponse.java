package kz.pelmen_delivery.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int code;

    private String message;

    private String status;

    public ErrorResponse(
            HttpStatus status,
            String message) {
        this.message = message;
        this.code = status.value();
        this.status = status.name();
    }

}
