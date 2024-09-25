package com.smartwash.application.funcionario.mapper;

import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.request.AtualizarFuncionarioRequest;
import com.smartwash.application.utils.enums.Status;

public class FuncionarioAtualizacaoRequestMapper {
    public static Funcionario toFuncionario(final AtualizarFuncionarioRequest atualizarFuncionarioRequest,
                                            Funcionario funcionario, Endereco endereco) {
        funcionario.setNome(atualizarFuncionarioRequest.nome());
        funcionario.setCpf(atualizarFuncionarioRequest.cpf());
        funcionario.setCelular(atualizarFuncionarioRequest.celular());
        funcionario.setDataContratacao(atualizarFuncionarioRequest.dataContratacao());
        funcionario.setEndereco(endereco);
        funcionario.setStatus(Status.ATIVO);
        
        return funcionario;
    }
}