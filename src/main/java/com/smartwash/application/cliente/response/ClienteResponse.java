package com.smartwash.application.cliente.response;

import com.smartwash.application.endereco.response.EnderecoResponse;
import com.smartwash.application.utils.enums.Resposta;
import com.smartwash.application.utils.enums.Status;

import java.util.UUID;

public record ClienteResponse(UUID id, String nome, String cpf, String celular, String email,
                              Resposta desejaSerNotificado, Status status, EnderecoResponse enderecoResponse) {
}
