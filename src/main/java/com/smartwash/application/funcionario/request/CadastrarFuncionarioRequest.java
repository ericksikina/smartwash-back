package com.smartwash.application.funcionario.request;

import com.smartwash.application.auth.request.RegisterRequest;
import com.smartwash.application.endereco.request.EnderecoRequest;

import java.time.LocalDate;

public record CadastrarFuncionarioRequest(String nome, String cpf, String celular, String salario,
                                          LocalDate dataContratacao, EnderecoRequest endereco, RegisterRequest auth) {
}