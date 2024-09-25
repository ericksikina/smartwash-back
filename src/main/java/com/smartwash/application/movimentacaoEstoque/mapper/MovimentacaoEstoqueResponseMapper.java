package com.smartwash.application.movimentacaoEstoque.mapper;

import com.smartwash.application.movimentacaoEstoque.model.MovimentacaoEstoque;
import com.smartwash.application.movimentacaoEstoque.response.MovimentacaoEstoqueRelatorioResponse;
import com.smartwash.application.utils.data.DateFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovimentacaoEstoqueResponseMapper {
    public static List<MovimentacaoEstoqueRelatorioResponse> toMovimentacaoEstoqueRelatorioResponse(final MovimentacaoEstoque movimentacaoEstoque){
        List<MovimentacaoEstoqueRelatorioResponse> listaDeMovimentacaoEstoque = new ArrayList<>();

        if(!Objects.isNull(movimentacaoEstoque.getBaixaEstoque())){
            movimentacaoEstoque.getBaixaEstoque().getListaDeBaixaEstoque().forEach(baixaEstoque -> {
                listaDeMovimentacaoEstoque.add(new MovimentacaoEstoqueRelatorioResponse(
                        DateFormatter.converterLocalDateTimeParaString(movimentacaoEstoque.getDataHora()),
                        movimentacaoEstoque.getTipoMovimentacao().getDescricao(),
                        baixaEstoque.getProduto().getDescricao(), baixaEstoque.getQuantidade()));
            });
        } else {
            movimentacaoEstoque.getCompra().getListaDeCompra().forEach(compra -> {
                listaDeMovimentacaoEstoque.add(new MovimentacaoEstoqueRelatorioResponse(
                        DateFormatter.converterLocalDateTimeParaString(movimentacaoEstoque.getDataHora()),
                        movimentacaoEstoque.getTipoMovimentacao().getDescricao(),
                        compra.getProduto().getDescricao(), compra.getQuantidade()));
            });
        }

        return listaDeMovimentacaoEstoque;
    }

    public static List<MovimentacaoEstoqueRelatorioResponse> toMovimentacaoEstoqueRelatorioResponseList(final List<MovimentacaoEstoque> listaDeMovimentacaoEstoques){
        List<MovimentacaoEstoqueRelatorioResponse> listaDeMovimentacaoEstoque = new ArrayList<>();
        listaDeMovimentacaoEstoques.forEach(movimentacaoEstoque -> {
            listaDeMovimentacaoEstoque.addAll(MovimentacaoEstoqueResponseMapper.toMovimentacaoEstoqueRelatorioResponse(movimentacaoEstoque));
        });

        return listaDeMovimentacaoEstoque;
    }
}