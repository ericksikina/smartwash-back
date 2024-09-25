package com.smartwash.application.funcionario.mapper;

import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.request.NovoSalarioRequest;

import java.math.BigDecimal;

public class NovoSalarioRequestMapper {
    public static void toFuncionario(NovoSalarioRequest novoSalarioRequest, Funcionario funcionario) {
        funcionario.setSalario(funcionario.getSalario().add(new BigDecimal(novoSalarioRequest.salario())));

    }
}