package com.smartwash.application.endereco.mapper;

import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.endereco.request.EnderecoRequest;

public class EnderecoRequestMapper {
    public static Endereco toEndereco(final EnderecoRequest enderecoRequest, Endereco endereco) {
        endereco.setRua(enderecoRequest.rua());
        endereco.setNumero(enderecoRequest.numero());
        endereco.setBairro(enderecoRequest.bairro());
        endereco.setCep(enderecoRequest.cep());
        endereco.setCidade(enderecoRequest.cidade());
        endereco.setEstado(enderecoRequest.estado());
        endereco.setComplemento(enderecoRequest.complemento());

        return endereco;
    }
}