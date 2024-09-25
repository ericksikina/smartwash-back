package com.smartwash.application.baixaEstoque.response;

import java.util.List;

public record BaixaEstoqueResponse(String dataHora, String funcionario,
                                   List<BaixaEstoqueDetalheResponse> listaDeProdutos) {
}