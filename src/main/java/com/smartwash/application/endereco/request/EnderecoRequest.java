package com.smartwash.application.endereco.request;

public record EnderecoRequest(String rua, String numero, String bairro, String cep,
                             String cidade, String estado, String complemento) {
}
