package com.smartwash.application.funcionario.response;

import com.smartwash.application.endereco.response.EnderecoResponse;
import com.smartwash.application.utils.enums.Status;

import java.util.UUID;

public record FuncionarioResponse(UUID id, String nome, String cpf, String celular, String salario,
                                  String dataContratacao, Status status, EnderecoResponse enderecoResponse) {
}