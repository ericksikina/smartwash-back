package com.smartwash.application.pagamento.request;

import com.smartwash.application.pagamento.enums.TipoPagamento;

public record PagamentoRequest(TipoPagamento tipo) {
}