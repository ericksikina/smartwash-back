package com.smartwash.application.compra.request;

import java.util.UUID;

public record CompraProdutoRequest(UUID produto, Long quantidade, String precoUnitario) {
}