package com.smartwash.application.endereco.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoViaCepRequest {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
