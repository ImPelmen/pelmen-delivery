package kz.pelmen_delivery.util;

import lombok.experimental.UtilityClass;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

@UtilityClass
public final class ModelMapperUtil {

    private final ModelMapper modelMapper = new ModelMapper();

    public static <T> T map(Object source, Class<T> destinationType) {
        return modelMapper().map(source, destinationType);
    }

    private static ModelMapper modelMapper() {
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());

        return modelMapper;
    }
}
