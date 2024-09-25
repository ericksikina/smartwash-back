package com.smartwash.application.produto.mapper;

import com.smartwash.application.produto.model.Produto;
import com.smartwash.application.produto.response.ProdutoRelatorioResponse;
import com.smartwash.application.produto.response.ProdutoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoResponseMapper {
    public static ProdutoResponse toProdutoResponse(final Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getDescricao(), produto.getQuantidadeEstoque(),
                produto.getEstoqueMinimo(), produto.getStatus());
    }

    public static List<ProdutoResponse> toProdutoResponseList(final List<Produto> listaDeProdutos) {
        return listaDeProdutos.stream()
                .map(ProdutoResponseMapper::toProdutoResponse)
                .collect(Collectors.toList());
    }

    public static ProdutoRelatorioResponse toProdutoRelatorioResponse(final Produto produto){
        return new ProdutoRelatorioResponse(produto.getDescricao(), produto.getQuantidadeEstoque(), produto.getEstoqueMinimo());
    }

    public static List<ProdutoRelatorioResponse> toProdutoRelatorioResponseList(final List<Produto> listaDeProdutos){
        return listaDeProdutos.stream()
                .map(ProdutoResponseMapper::toProdutoRelatorioResponse)
                .collect(Collectors.toList());
    }
}