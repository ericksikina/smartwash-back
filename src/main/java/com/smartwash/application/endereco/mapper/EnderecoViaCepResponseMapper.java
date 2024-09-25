package com.smartwash.application.endereco.mapper;

import com.smartwash.application.endereco.request.EnderecoViaCepRequest;
import com.smartwash.application.endereco.response.EnderecoViaCepResponse;

public class EnderecoViaCepResponseMapper {
    public static EnderecoViaCepResponse toEnderecoViaCepResponse(final EnderecoViaCepRequest enderecoViaCepRequest) {
        return new EnderecoViaCepResponse(enderecoViaCepRequest.getLogradouro(), enderecoViaCepRequest.getBairro(),
                enderecoViaCepRequest.getCep(),enderecoViaCepRequest.getLocalidade(), enderecoViaCepRequest.getUf());
    }
}
