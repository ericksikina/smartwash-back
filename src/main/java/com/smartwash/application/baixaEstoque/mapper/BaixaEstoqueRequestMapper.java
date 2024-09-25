package com.smartwash.application.baixaEstoque.mapper;

import com.smartwash.application.baixaEstoque.model.BaixaEstoque;
import com.smartwash.application.funcionario.model.Funcionario;

import java.time.LocalDateTime;

public class BaixaEstoqueRequestMapper {
    public static BaixaEstoque toBaixaEstoque(BaixaEstoque baixaEstoque, Funcionario funcionario) {
        baixaEstoque.setDataHora(LocalDateTime.now());
        baixaEstoque.setFuncionario(funcionario);

        return baixaEstoque;
    }
}