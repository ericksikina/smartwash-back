package com.smartwash.application.pagamento.enums;

public enum TipoPagamento {
    PIX("P"),
    CREDITO("C"),
    DEBITO("D"),
    EM_ESPECIE("E");

    private final String value;

    TipoPagamento(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
