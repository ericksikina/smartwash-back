package com.smartwash.application.movimentacaoCaixa.mapper;

import com.smartwash.application.movimentacaoCaixa.model.MovimentacaoCaixa;
import com.smartwash.application.movimentacaoCaixa.response.MovimentacaoCaixaRelatorioResponse;
import com.smartwash.application.movimentacaoEstoque.enums.TipoMovimentacao;
import com.smartwash.application.utils.data.DateFormatter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovimentacaoCaixaResponseMapper {
    public static MovimentacaoCaixaRelatorioResponse toMovimentacaoCaixaRelatorioResponse(MovimentacaoCaixa movimentacaoCaixa) {
        MovimentacaoCaixaRelatorioResponse movimentacaoCaixaRelatorioResponse = new MovimentacaoCaixaRelatorioResponse();
        if (!Objects.isNull(movimentacaoCaixa.getCompra())) {
            movimentacaoCaixaRelatorioResponse.setDataHora(DateFormatter.converterLocalDateTimeParaString(movimentacaoCaixa.getDataHora()));
            movimentacaoCaixaRelatorioResponse.setTipoMovimentacao(TipoMovimentacao.SAIDA.getDescricao());
            movimentacaoCaixaRelatorioResponse.setTotal(movimentacaoCaixa.getCompra().getValorTotal());
            movimentacaoCaixaRelatorioResponse.setFuncionaio(movimentacaoCaixa.getCompra().getFuncionario().getNome());
        } else {
            movimentacaoCaixaRelatorioResponse.setDataHora(DateFormatter.converterLocalDateTimeParaString(movimentacaoCaixa.getDataHora()));
            movimentacaoCaixaRelatorioResponse.setTipoMovimentacao(TipoMovimentacao.ENTRADA.getDescricao());
            movimentacaoCaixaRelatorioResponse.setTotal(movimentacaoCaixa.getPedido().getValorTotal());
            movimentacaoCaixaRelatorioResponse.setFuncionaio(movimentacaoCaixa.getPedido().getFuncionario().getNome());
        }

        return movimentacaoCaixaRelatorioResponse;
    }

    public static List<MovimentacaoCaixaRelatorioResponse> toMovimentacaoCaixaRelatorioResponseList(List<MovimentacaoCaixa> listaDeMovimentacaoCaixa) {
        return listaDeMovimentacaoCaixa.stream()
                .map(MovimentacaoCaixaResponseMapper::toMovimentacaoCaixaRelatorioResponse)
                .collect(Collectors.toList());
    }
}