package com.smartwash.application.compra.mapper;

import com.smartwash.application.compra.model.Compra;
import com.smartwash.application.compra.model.CompraDetalhe;
import com.smartwash.application.compra.response.CompraDetalheResponse;
import com.smartwash.application.compra.response.CompraResponse;
import com.smartwash.application.utils.data.DateFormatter;

import java.util.List;
import java.util.stream.Collectors;

public class CompraResponseMapper {
    public static CompraResponse toCompraResponse(final Compra compra) {
        return new CompraResponse(compra.getValorTotal().toString(), DateFormatter.converterLocalDateTimeParaString(compra.getDataHora()),
                compra.getFornecedor().getDescricao(),
                compra.getFuncionario().getNome(),
                CompraResponseMapper.toCompraDetalheResponseList(compra.getListaDeCompra().stream().toList()));
    }

    public static List<CompraResponse> toCompraResponseList(final List<Compra> listaDeCompras) {
        return listaDeCompras.stream()
                .map(CompraResponseMapper::toCompraResponse)
                .collect(Collectors.toList());
    }

    public static CompraDetalheResponse toCompraDetalheResponse(final CompraDetalhe compraDetalhe) {
        return new CompraDetalheResponse(compraDetalhe.getProduto().getDescricao(), compraDetalhe.getQuantidade(),
                compraDetalhe.getPrecoUnitario().toString());
    }

    public static List<CompraDetalheResponse> toCompraDetalheResponseList(final List<CompraDetalhe> listaDeCompras) {
        return listaDeCompras.stream()
                .map(CompraResponseMapper::toCompraDetalheResponse)
                .collect(Collectors.toList());
    }
}