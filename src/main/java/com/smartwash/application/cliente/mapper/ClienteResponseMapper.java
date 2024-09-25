package com.smartwash.application.cliente.mapper;

import com.smartwash.application.cliente.model.Cliente;
import com.smartwash.application.cliente.response.ClienteResponse;
import com.smartwash.application.endereco.mapper.EnderecoResponseMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteResponseMapper {
    public static ClienteResponse toClienteResponse(final Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getCelular(),
                cliente.getEmail(), cliente.getDesejaSerNotificado(), cliente.getStatus(),
                EnderecoResponseMapper.toEnderecoResponse(cliente.getEndereco()));
    }

    public static List<ClienteResponse> toClienteResponseList(final List<Cliente> listaDeClientes) {
        return listaDeClientes.stream()
                .map(ClienteResponseMapper::toClienteResponse)
                .collect(Collectors.toList());
    }
}
