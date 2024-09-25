package com.smartwash.application.utils.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertEnumResposta implements AttributeConverter<Resposta, String> {
    @Override
    public String convertToDatabaseColumn(Resposta attribute) {
        return Objects.isNull(attribute) ? null : attribute.getValue();
    }

    @Override
    public Resposta convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        return Stream.of(Resposta.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
