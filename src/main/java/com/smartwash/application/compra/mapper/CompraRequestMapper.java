package com.smartwash.application.compra.mapper;

import com.smartwash.application.compra.model.Compra;
import com.smartwash.application.compra.request.CompraRequest;
import com.smartwash.application.fornecedor.model.Fornecedor;
import com.smartwash.application.funcionario.model.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompraRequestMapper {
    public static Compra toCompra(final CompraRequest compraRequest, Compra compra, Fornecedor fornecedor,
                                  Funcionario funcionario) {
        compra.setFornecedor(fornecedor);
        compra.setDataHora(LocalDateTime.now());
        compra.setFuncionario(funcionario);
        compra.setValorTotal(new BigDecimal(compraRequest.valorTotal()));

        return compra;
    }
}