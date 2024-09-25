package com.smartwash.application.movimentacaoEstoque.controller;

import com.smartwash.application.movimentacaoEstoque.service.MovimentacaoEstoqueService;
import com.smartwash.application.utils.request.DataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimentacao-estoque")
public class MovimentacaoEstoqueController {
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Autowired
    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @PostMapping("/gerar-relatorio")
    public ResponseEntity<byte[]> gerarRelatorioDeMovimentacaoEstoque(
            @RequestBody DataRequest dataRequest){
        return this.movimentacaoEstoqueService.gerarRelatorioDeMovimentacaoEstoque(dataRequest);
    }
}