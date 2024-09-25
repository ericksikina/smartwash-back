package com.smartwash.application.servico.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ServicoRelatorioResponse {
    private String descricao;
    private BigDecimal preco;
}