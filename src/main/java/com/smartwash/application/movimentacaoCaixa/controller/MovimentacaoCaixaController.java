package com.smartwash.application.movimentacaoCaixa.controller;

import com.smartwash.application.movimentacaoCaixa.service.MovimentacaoCaixaService;
import com.smartwash.application.utils.request.DataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimentacao-caixa")
public class MovimentacaoCaixaController {
    private final MovimentacaoCaixaService movimentacaoCaixaService;

    @Autowired
    public MovimentacaoCaixaController(MovimentacaoCaixaService movimentacaoCaixaService){
        this.movimentacaoCaixaService = movimentacaoCaixaService;
    }

    @PostMapping("/gerar-relatorio")
    public ResponseEntity<byte[]> gerarRelatorioDeMovimentacaoCaixa(
            @RequestBody DataRequest dataRequest){
        return this.movimentacaoCaixaService.gerarRelatorioDeMovimentacaoCaixa(dataRequest);
    }
}