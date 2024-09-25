package com.smartwash.application.fornecedor.mapper;

import com.smartwash.application.fornecedor.model.Fornecedor;
import com.smartwash.application.fornecedor.request.FornecedorRequest;
import com.smartwash.application.utils.enums.Status;

public class FornecedorRequestMapper {
    public static Fornecedor toFornecedor(final FornecedorRequest fornecedorRequest, Fornecedor fornecedor) {
        fornecedor.setDescricao(fornecedorRequest.descricao());
        fornecedor.setTelefone(fornecedorRequest.telefone());
        fornecedor.setCnpj(fornecedorRequest.cnpj());
        fornecedor.setStatus(Status.ATIVO);

        return fornecedor;
    }
}
