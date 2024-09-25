package com.smartwash.application.pedido.response;

import com.smartwash.application.pagamento.response.PagamentoResponse;
import com.smartwash.application.pedido.enums.StatusPedido;

import java.util.List;
import java.util.UUID;

public record PedidoResponse(UUID id, String dataHora, String valorTotal, StatusPedido status,
                             String funcionario, String cliente, PagamentoResponse pagamento,
                             List<PedidoDetalheResponse> listaDeServico) {
}