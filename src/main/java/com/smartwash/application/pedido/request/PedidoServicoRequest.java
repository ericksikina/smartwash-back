package com.smartwash.application.pedido.request;

import java.util.UUID;

public record PedidoServicoRequest(UUID servico, Long quantidade) {
}