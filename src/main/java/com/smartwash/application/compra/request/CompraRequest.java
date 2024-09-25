package com.smartwash.application.compra.request;

import java.util.List;
import java.util.UUID;

public record CompraRequest(UUID fornecedor, String valorTotal, List<CompraProdutoRequest> listaDeProdutos) {
}