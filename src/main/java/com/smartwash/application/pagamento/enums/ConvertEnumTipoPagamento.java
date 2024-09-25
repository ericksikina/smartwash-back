package com.smartwash.application.pagamento.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertEnumTipoPagamento implements AttributeConverter<TipoPagamento, String> {
    @Override
    public String convertToDatabaseColumn(TipoPagamento attribute) {
        return Objects.isNull(attribute) ? null : attribute.getValue();
    }

    @Override
    public TipoPagamento convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        return Stream.of(TipoPagamento.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
