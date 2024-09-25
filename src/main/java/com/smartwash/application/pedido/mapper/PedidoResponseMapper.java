package com.smartwash.application.pedido.mapper;

import com.smartwash.application.pagamento.mapper.PagamentoResponseMapper;
import com.smartwash.application.pedido.model.Pedido;
import com.smartwash.application.pedido.model.PedidoDetalhe;
import com.smartwash.application.pedido.response.PedidoDetalheResponse;
import com.smartwash.application.pedido.response.PedidoRelatorioResponse;
import com.smartwash.application.pedido.response.PedidoResponse;
import com.smartwash.application.utils.data.DateFormatter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PedidoResponseMapper {
    public static PedidoResponse toPedidoResponse(final Pedido pedido) {
        return new PedidoResponse(pedido.getId(), DateFormatter.converterLocalDateTimeParaString(pedido.getDataHora()), pedido.getValorTotal().toString(),
                pedido.getStatus(), pedido.getFuncionario().getNome(), pedido.getCliente().getNome(),
                !Objects.isNull(pedido.getPagamento()) ?
                        PagamentoResponseMapper.toPagamentoResponseResponse(pedido.getPagamento()) : null,
                PedidoResponseMapper.toPedidoDetalheResponseList(pedido.getListaDeServicos().stream().toList()));
    }

    public static List<PedidoResponse> toPedidoResponseList(final List<Pedido> listaDePedidos) {
        return listaDePedidos.stream()
                .map(PedidoResponseMapper::toPedidoResponse)
                .collect(Collectors.toList());
    }

    public static PedidoDetalheResponse PedidoDetalheResponse(final PedidoDetalhe pedidoDetalhe){
        return new PedidoDetalheResponse(pedidoDetalhe.getServico().getDescricao(), pedidoDetalhe.getQuantidade());
    }

    public static List<PedidoDetalheResponse> toPedidoDetalheResponseList(final List<PedidoDetalhe> listaDeServicos) {
        return listaDeServicos.stream()
                .map(PedidoResponseMapper::PedidoDetalheResponse)
                .collect(Collectors.toList());
    }

    public static PedidoRelatorioResponse toPedidoRelatorioResponse(final Pedido pedido){
        return new PedidoRelatorioResponse(DateFormatter.converterLocalDateTimeParaString(pedido.getDataHora()),
                pedido.getValorTotal(), pedido.getCliente().getNome(),
                converterListaDeServicosEmString(pedido.getListaDeServicos()));
    }

    public static List<PedidoRelatorioResponse> toPedidoRelatorioResponseList(List<Pedido> lisaDePedidos){
        return lisaDePedidos.stream()
                .map(PedidoResponseMapper::toPedidoRelatorioResponse)
                .collect(Collectors.toList());
    }

    public static String converterListaDeServicosEmString(final List<PedidoDetalhe> listaDePedidoDetalhe){
        StringBuilder pedido = new StringBuilder();
        int tamanho = 0;
        for(PedidoDetalhe pedidoDetalhe: listaDePedidoDetalhe){
            pedido.append(pedidoDetalhe.getQuantidade().toString()).append(" ");
            pedido.append(pedidoDetalhe.getServico().getDescricao());
            tamanho++;
            if(tamanho <= listaDePedidoDetalhe.size()-1){
                pedido.append(", ");
            }
        }

        return pedido.toString();
    }
}