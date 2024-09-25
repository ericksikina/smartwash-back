package com.smartwash.application.funcionario.mapper;

import com.smartwash.application.endereco.mapper.EnderecoResponseMapper;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.response.FuncionarioRelatorioResponse;
import com.smartwash.application.funcionario.response.FuncionarioResponse;
import com.smartwash.application.utils.data.DateFormatter;

import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioResponseMapper {
    public static FuncionarioResponse toFuncionarioResponse(final Funcionario funcionario) {
        return new FuncionarioResponse(funcionario.getId(), funcionario.getNome(), funcionario.getCpf(),
                funcionario.getCelular(), funcionario.getSalario().toString(),
                DateFormatter.converterLocalDateParaString(funcionario.getDataContratacao()), funcionario.getStatus(),
                EnderecoResponseMapper.toEnderecoResponse(funcionario.getEndereco()));
    }

    public static List<FuncionarioResponse> toFuncionarioResponseList(final List<Funcionario> listaDeFuncionarios) {
        return listaDeFuncionarios.stream()
                .map(FuncionarioResponseMapper::toFuncionarioResponse)
                .collect(Collectors.toList());
    }

    public static FuncionarioRelatorioResponse toFuncionarioRelatorioResponse(final Funcionario funcionario){
        return new FuncionarioRelatorioResponse(funcionario.getNome(), funcionario.getCelular(), funcionario.getSalario());
    }

    public static List<FuncionarioRelatorioResponse> toFuncionarioRelatorioResponseList(final List<Funcionario> listaDeFuncionarios){
        return listaDeFuncionarios.stream()
                .map(FuncionarioResponseMapper::toFuncionarioRelatorioResponse)
                .collect(Collectors.toList());
    }
}