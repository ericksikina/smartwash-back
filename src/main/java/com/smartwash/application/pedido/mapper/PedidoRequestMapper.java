package com.smartwash.application.pedido.mapper;

import com.smartwash.application.cliente.model.Cliente;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.pedido.enums.StatusPedido;
import com.smartwash.application.pedido.model.Pedido;
import com.smartwash.application.pedido.request.PedidoRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoRequestMapper {
    public static Pedido toPedido(final PedidoRequest pedidoRequest, Pedido pedido,
                                  Funcionario funcionario, Cliente cliente) {
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setValorTotal(new BigDecimal(pedidoRequest.valorTotal()));
        pedido.setDataHora(LocalDateTime.now());
        pedido.setFuncionario(funcionario);
        pedido.setCliente(cliente);

        return pedido;
    }
}