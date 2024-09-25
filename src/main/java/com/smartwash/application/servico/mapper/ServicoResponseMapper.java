package com.smartwash.application.servico.mapper;

import com.smartwash.application.servico.model.Servico;
import com.smartwash.application.servico.response.ServicoRelatorioResponse;
import com.smartwash.application.servico.response.ServicoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ServicoResponseMapper {
    public static ServicoResponse toServicoResponse(final Servico servico) {
        return new ServicoResponse(servico.getId(), servico.getDescricao(), servico.getPreco().toString(), servico.getStatus());
    }

    public static List<ServicoResponse> toServicoResponseList(final List<Servico> listaDeServicos) {
        return listaDeServicos.stream()
                .map(ServicoResponseMapper::toServicoResponse)
                .collect(Collectors.toList());
    }

    public static ServicoRelatorioResponse toServicoRelatorioResponse(final Servico servico){
        return new ServicoRelatorioResponse(servico.getDescricao(), servico.getPreco());
    }

    public static List<ServicoRelatorioResponse> toServicoRelatorioResponseList(final List<Servico> listaDeServicos){
        return listaDeServicos.stream()
                .map(ServicoResponseMapper::toServicoRelatorioResponse)
                .collect(Collectors.toList());
    }
}