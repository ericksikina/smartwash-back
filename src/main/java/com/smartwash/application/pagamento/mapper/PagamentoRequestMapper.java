package com.smartwash.application.pagamento.mapper;

import com.smartwash.application.pagamento.model.Pagamento;
import com.smartwash.application.pagamento.request.PagamentoRequest;

import java.time.LocalDateTime;

public class PagamentoRequestMapper {
    public static Pagamento toPagamento(final PagamentoRequest pagamentoRequest, Pagamento pagamento) {
        pagamento.setTipo(pagamentoRequest.tipo());
        pagamento.setDataHora(LocalDateTime.now());

        return pagamento;
    }
}
