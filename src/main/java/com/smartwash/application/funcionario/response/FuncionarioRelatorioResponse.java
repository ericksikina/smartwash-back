package com.smartwash.application.funcionario.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class FuncionarioRelatorioResponse {
    private String nome;
    private String celular;
    private BigDecimal salario;
}