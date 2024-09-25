package com.smartwash.application.produto.response;

import com.smartwash.application.utils.enums.Status;

import java.util.UUID;

public record ProdutoResponse(UUID id, String descricao, int quantidade, int estoqueMinimo,
                              Status status) {
}
