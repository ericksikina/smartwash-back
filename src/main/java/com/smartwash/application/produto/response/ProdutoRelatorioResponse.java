package com.smartwash.application.produto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoRelatorioResponse {
    private String descricao;
    private int quantidadeEstoque;
    private int estoqueMinimo;
}