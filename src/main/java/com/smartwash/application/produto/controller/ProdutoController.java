package com.smartwash.application.produto.controller;

import com.smartwash.application.produto.request.AtualizarProdutoRequest;
import com.smartwash.application.produto.request.CadastrarProdutoRequest;
import com.smartwash.application.produto.response.ProdutoResponse;
import com.smartwash.application.produto.service.ProdutoService;
import com.smartwash.application.utils.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    
    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService =  produtoService;
    }
    
    @GetMapping("/listar/{status}")
    public ResponseEntity<List<ProdutoResponse>> buscarListaDeProdutos(@PathVariable Status status) {
        List<ProdutoResponse> listaDeProdutoResponse = this.produtoService.buscarListaDeProdutos(status);
        
        return ResponseEntity.ok(listaDeProdutoResponse);
    }

    @GetMapping("/gerar-relatorio")
    public ResponseEntity<byte[]> gerarRelatorioDeProduto(){
        return this.produtoService.gerarRelatorioDeProduto(false);
    }

    @GetMapping("/gerar-relatorio-estoque-baixo")
    public ResponseEntity<byte[]> gerarRelatorioDeProdutoComEstoqueBaixo(){
        return this.produtoService.gerarRelatorioDeProduto(true);
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarProduto(@RequestBody CadastrarProdutoRequest cadastrarProdutoRequest) {
        this.produtoService.cadastrarProduto(cadastrarProdutoRequest);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarProduto(@PathVariable UUID id, 
                                                 @RequestBody AtualizarProdutoRequest atualizarProdutoRequest) {
        this.produtoService.atualizarProduto(id, atualizarProdutoRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/atualizar-situacao/{id}")
    public ResponseEntity<Void> atualizarSituacaoProduto(@PathVariable UUID id) {
        this.produtoService.atualizarSituacaoProduto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}