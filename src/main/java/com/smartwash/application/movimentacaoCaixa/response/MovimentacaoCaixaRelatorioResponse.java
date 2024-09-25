package com.smartwash.application.movimentacaoCaixa.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoCaixaRelatorioResponse {
    private String dataHora;
    private String tipoMovimentacao;
    private BigDecimal total;
    private String funcionaio;
}