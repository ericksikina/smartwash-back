package com.smartwash.application.pagamento.response;

import com.smartwash.application.pagamento.enums.TipoPagamento;

public record PagamentoResponse(String dataPagamento, TipoPagamento tipo){
}