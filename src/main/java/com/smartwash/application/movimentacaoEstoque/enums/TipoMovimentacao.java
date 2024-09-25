package com.smartwash.application.movimentacaoEstoque.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMovimentacao {
    ENTRADA("E", "Entrada"),
    SAIDA("S", "Saída");

    private final String value;
    private final String descricao;
}