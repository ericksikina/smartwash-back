package com.smartwash.application.baixaEstoque.request;

import java.util.List;

public record BaixaEstoqueRequest(List<BaixaEstoqueProdutoRequest> listaDeProdutos) {
}