package com.smartwash.application.servico.mapper;

import com.smartwash.application.servico.model.Servico;
import com.smartwash.application.servico.request.ServicoRequest;
import com.smartwash.application.utils.enums.Status;

import java.math.BigDecimal;

public class ServicoRequestMapper {
    public static Servico toServico(final ServicoRequest servicoRequest, Servico servico) {
        servico.setDescricao(servicoRequest.descricao());
        servico.setPreco(new BigDecimal(servicoRequest.preco()));
        servico.setStatus(Status.ATIVO);

        return servico;
    }
}