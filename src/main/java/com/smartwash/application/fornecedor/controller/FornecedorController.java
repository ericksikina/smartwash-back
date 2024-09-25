package com.smartwash.application.fornecedor.controller;

import com.smartwash.application.fornecedor.request.FornecedorRequest;
import com.smartwash.application.fornecedor.response.FornecedorResponse;
import com.smartwash.application.fornecedor.service.FornecedorService;
import com.smartwash.application.utils.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
    private final FornecedorService fornecedorService;

    @Autowired
    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping("/listar/{status}")
    public ResponseEntity<List<FornecedorResponse>> buscarListaDeFornecedores(@PathVariable Status status){
        List<FornecedorResponse> listaDeForncedoresResponse = this.fornecedorService.buscarListaDeFornecedores(status);

        return ResponseEntity.ok(listaDeForncedoresResponse);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadsatrarForncedor(@RequestBody FornecedorRequest fornecedorRequest){
        this.fornecedorService.cadsatrarForncedor(fornecedorRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atalizarForncedor(@PathVariable UUID id, @RequestBody FornecedorRequest fornecedorRequest){
        this.fornecedorService.atalizarForncedor(id, fornecedorRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/atualizar-situacao/{id}")
    public ResponseEntity<Void> atualizarSitucaoFornecedor(@PathVariable UUID id){
        this.fornecedorService.atualizarSitucaoFornecedor(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}