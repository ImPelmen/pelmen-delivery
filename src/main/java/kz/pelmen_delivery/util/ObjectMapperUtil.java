package kz.pelmen_delivery.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtil {

    private final ObjectMapper objectMapper;

    public <T> T convert(Object source, Class<T> target) {
        try {
            return objectMapper.convertValue(source, target);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Object can not be converted: %s", source));
        }
    }
}
