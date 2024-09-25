package com.smartwash.application.movimentacaoEstoque.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEstoqueRelatorioResponse {
    private String dataHora;
    private String tipoMovimentacao;
    private String produto;
    private Long quantidade;
}