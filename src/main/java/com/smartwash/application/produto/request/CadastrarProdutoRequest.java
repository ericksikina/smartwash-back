package com.smartwash.application.produto.request;

public record CadastrarProdutoRequest(String descricao, int quantidadeEstoque, int estoqueMinimo) {
}