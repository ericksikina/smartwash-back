package com.smartwash.application.pedido.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRelatorioResponse {
    private String dataHora;
    private BigDecimal total;
    private String cliente;
    private String servicos;
}