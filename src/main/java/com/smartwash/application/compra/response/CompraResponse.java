package com.smartwash.application.compra.response;

import java.util.List;

public record CompraResponse(String valorTotal, String dataHora, String fornecedor,
                             String funcionario, List<CompraDetalheResponse> listaDeCompra) {
}