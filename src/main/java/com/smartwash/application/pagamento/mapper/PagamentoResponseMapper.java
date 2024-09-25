package com.smartwash.application.pagamento.mapper;


import com.smartwash.application.pagamento.model.Pagamento;
import com.smartwash.application.pagamento.response.PagamentoResponse;
import com.smartwash.application.utils.data.DateFormatter;

public class PagamentoResponseMapper {
    public static PagamentoResponse toPagamentoResponseResponse(final Pagamento pagamento) {
        return new PagamentoResponse(DateFormatter.converterLocalDateTimeParaString(pagamento.getDataHora()), pagamento.getTipo());
    }
}