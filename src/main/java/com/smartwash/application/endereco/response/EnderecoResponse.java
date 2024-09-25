package com.smartwash.application.endereco.response;

import java.util.UUID;

public record EnderecoResponse(UUID id, String rua, String numero, String bairro,
                               String cep, String cidade, String estado, String complemento) {
}
