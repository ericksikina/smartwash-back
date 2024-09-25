package com.smartwash.application.endereco.mapper;

import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.endereco.response.EnderecoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoResponseMapper {
    public static EnderecoResponse toEnderecoResponse(final Endereco endereco) {
        return new EnderecoResponse(endereco.getId(), endereco.getRua(), endereco.getNumero(), endereco.getBairro(),
                endereco.getCep(), endereco.getCidade(), endereco.getEstado(), endereco.getComplemento());
    }

    public static List<EnderecoResponse> toEnderecoResponseList(final List<Endereco> listaDeEnderecos) {
        return listaDeEnderecos.stream()
                .map(EnderecoResponseMapper::toEnderecoResponse)
                .collect(Collectors.toList());
    }
}
