package com.smartwash.application.servico.response;

import com.smartwash.application.utils.enums.Status;

import java.util.UUID;

public record ServicoResponse( UUID id, String descricao, String preco, Status status) {
}