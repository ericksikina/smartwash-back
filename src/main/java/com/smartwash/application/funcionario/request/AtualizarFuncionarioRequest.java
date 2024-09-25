package com.smartwash.application.funcionario.request;

import com.smartwash.application.endereco.request.EnderecoRequest;

import java.time.LocalDate;

public record AtualizarFuncionarioRequest(String nome, String cpf, String celular,
                                          LocalDate dataContratacao, EnderecoRequest endereco) {
}