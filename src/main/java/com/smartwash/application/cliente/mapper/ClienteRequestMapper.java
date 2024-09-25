package com.smartwash.application.cliente.mapper;

import com.smartwash.application.cliente.model.Cliente;
import com.smartwash.application.cliente.request.ClienteRequest;
import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.utils.enums.Status;

public class ClienteRequestMapper {
    public static Cliente ClienteRequestToCliente(final ClienteRequest atualizarClienteRequest, Cliente cliente, Endereco endereco) {
        cliente.setNome(atualizarClienteRequest.nome());
        cliente.setCpf(atualizarClienteRequest.cpf());
        cliente.setCelular(atualizarClienteRequest.celular());
        cliente.setEmail(atualizarClienteRequest.email());
        cliente.setDesejaSerNotificado(atualizarClienteRequest.desejaSerNotificado());
        cliente.setEndereco(endereco);
        cliente.setStatus(Status.ATIVO);

        return cliente;
    }
}