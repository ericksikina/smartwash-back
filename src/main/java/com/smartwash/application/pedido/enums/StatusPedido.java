package com.smartwash.application.pedido.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedido {
    RECEBIDO("R"),
    PRONTO("P"),
    FINALIZADO("F");

    private final String value;
}
