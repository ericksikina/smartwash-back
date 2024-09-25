package com.smartwash.application.baixaEstoque.request;

import java.util.UUID;

public record BaixaEstoqueProdutoRequest(UUID produto, Long quantidade) {
}