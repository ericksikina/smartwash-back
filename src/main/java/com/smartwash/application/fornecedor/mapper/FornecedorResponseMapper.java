package com.smartwash.application.fornecedor.mapper;

import com.smartwash.application.fornecedor.model.Fornecedor;
import com.smartwash.application.fornecedor.response.FornecedorResponse;

import java.util.List;
import java.util.stream.Collectors;

public class FornecedorResponseMapper {
    public static FornecedorResponse toFornecedorResponse(final Fornecedor fornecedor) {
        return new FornecedorResponse(fornecedor.getId(), fornecedor.getDescricao(),
                fornecedor.getTelefone(), fornecedor.getCnpj(), fornecedor.getStatus());
    }

    public static List<FornecedorResponse> toFornecedorResponseList(final List<Fornecedor> listaDeFornecedors) {
        return listaDeFornecedors.stream()
                .map(FornecedorResponseMapper::toFornecedorResponse)
                .collect(Collectors.toList());
    }
}
