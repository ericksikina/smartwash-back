package com.smartwash.application.pedido.request;

import java.util.List;
import java.util.UUID;

public record PedidoRequest(String valorTotal, UUID cliente,
                            List<PedidoServicoRequest> listaDeServicos) {
}