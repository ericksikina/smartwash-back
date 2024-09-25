package com.smartwash.application.cliente.request;

import com.smartwash.application.endereco.request.EnderecoRequest;
import com.smartwash.application.utils.enums.Resposta;

public record ClienteRequest(String nome, String cpf, String celular, String email,
                             Resposta desejaSerNotificado, EnderecoRequest endereco) {
}