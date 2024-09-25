package com.smartwash.application.baixaEstoque.mapper;

import com.smartwash.application.baixaEstoque.model.BaixaEstoque;
import com.smartwash.application.baixaEstoque.model.BaixaEstoqueDetalhe;
import com.smartwash.application.baixaEstoque.response.BaixaEstoqueDetalheResponse;
import com.smartwash.application.baixaEstoque.response.BaixaEstoqueResponse;
import com.smartwash.application.utils.data.DateFormatter;

import java.util.List;
import java.util.stream.Collectors;

public class BaixaEstoqueResponseMapper {
    public static BaixaEstoqueResponse toBaixaEstoqueResponse(final BaixaEstoque baixaEstoque) {
        return new BaixaEstoqueResponse(DateFormatter.converterLocalDateTimeParaString(baixaEstoque.getDataHora()), baixaEstoque.getFuncionario().getNome(),
                BaixaEstoqueResponseMapper.toBaixaEstoqueDetalheResponseList(baixaEstoque.getListaDeBaixaEstoque().stream().toList()));
    }

    public static List<BaixaEstoqueResponse> toBaixaEstoqueResponseList(final List<BaixaEstoque> listaDeBaixaEstoques) {
        return listaDeBaixaEstoques.stream()
                .map(BaixaEstoqueResponseMapper::toBaixaEstoqueResponse)
                .collect(Collectors.toList());
    }

    public static BaixaEstoqueDetalheResponse toBaixaEstoqueDetalheResponse(final BaixaEstoqueDetalhe baixaEstoqueDetalhe) {
        return new BaixaEstoqueDetalheResponse(baixaEstoqueDetalhe.getProduto().getDescricao(), baixaEstoqueDetalhe.getQuantidade());
    }

    public static List<BaixaEstoqueDetalheResponse> toBaixaEstoqueDetalheResponseList(final List<BaixaEstoqueDetalhe> listaDeBaixaEstoqueDetalhe) {
        return listaDeBaixaEstoqueDetalhe.stream()
                .map(BaixaEstoqueResponseMapper::toBaixaEstoqueDetalheResponse)
                .collect(Collectors.toList());
    }
}