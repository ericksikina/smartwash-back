package com.smartwash.application.produto.mapper;

import com.smartwash.application.produto.model.Produto;
import com.smartwash.application.produto.request.AtualizarProdutoRequest;
import com.smartwash.application.produto.request.CadastrarProdutoRequest;
import com.smartwash.application.utils.enums.Status;

public class ProdutoRequestMapper {
    public static Produto cadastrarProdutoRequestToProduto(final CadastrarProdutoRequest cadastrarProdutoRequest, Produto produto) {
        produto.setDescricao(cadastrarProdutoRequest.descricao());
        produto.setQuantidadeEstoque(cadastrarProdutoRequest.quantidadeEstoque());
        produto.setEstoqueMinimo(cadastrarProdutoRequest.estoqueMinimo());
        produto.setStatus(Status.ATIVO);

        return produto;
    }

    public static Produto atualizarProdutoRequestToProduto(final AtualizarProdutoRequest atualizarProdutoRequest, Produto produto) {
        produto.setDescricao(atualizarProdutoRequest.descricao());
        produto.setEstoqueMinimo(atualizarProdutoRequest.estoqueMinimo());
        produto.setStatus(Status.ATIVO);

        return produto;
    }
}