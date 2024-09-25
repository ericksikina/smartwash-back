package com.smartwash.application.movimentacaoEstoque.enums;

import com.smartwash.application.pagamento.enums.TipoPagamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertEnumTipoMovimentacao implements AttributeConverter<TipoMovimentacao, String> {
    @Override
    public String convertToDatabaseColumn(TipoMovimentacao attribute) {
        return Objects.isNull(attribute) ? null : attribute.getValue();
    }

    @Override
    public TipoMovimentacao convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        return Stream.of(TipoMovimentacao.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
