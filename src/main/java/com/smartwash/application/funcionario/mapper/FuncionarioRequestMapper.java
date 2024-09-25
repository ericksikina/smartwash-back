package com.smartwash.application.funcionario.mapper;

import com.smartwash.application.auth.model.Auth;
import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.request.CadastrarFuncionarioRequest;
import com.smartwash.application.utils.enums.Status;

import java.math.BigDecimal;

public class FuncionarioRequestMapper {
    public static Funcionario toFuncionario(final CadastrarFuncionarioRequest cadastrarFuncionarioRequest, Funcionario funcionario,
                                            Endereco endereco, Auth auth) {
        funcionario.setNome(cadastrarFuncionarioRequest.nome());
        funcionario.setCpf(cadastrarFuncionarioRequest.cpf());
        funcionario.setCelular(cadastrarFuncionarioRequest.celular());
        funcionario.setSalario(new BigDecimal(cadastrarFuncionarioRequest.salario()));
        funcionario.setDataContratacao(cadastrarFuncionarioRequest.dataContratacao());
        funcionario.setEndereco(endereco);
        funcionario.setStatus(Status.ATIVO);
        funcionario.setAuth(auth);

        return funcionario;
    }
}