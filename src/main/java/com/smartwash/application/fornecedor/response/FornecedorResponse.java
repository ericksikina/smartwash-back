package com.smartwash.application.fornecedor.response;

import com.smartwash.application.utils.enums.Status;

import java.util.UUID;

public record FornecedorResponse(UUID id, String descricao, String telefone, String cnpj, Status status) {
}
