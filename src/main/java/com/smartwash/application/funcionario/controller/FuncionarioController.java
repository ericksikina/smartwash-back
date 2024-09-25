package com.smartwash.application.funcionario.controller;

import com.smartwash.application.funcionario.request.AtualizarFuncionarioRequest;
import com.smartwash.application.funcionario.request.CadastrarFuncionarioRequest;
import com.smartwash.application.funcionario.request.NovoSalarioRequest;
import com.smartwash.application.funcionario.response.FuncionarioResponse;
import com.smartwash.application.funcionario.service.FuncionarioService;
import com.smartwash.application.utils.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/listar/{status}")
    public ResponseEntity<List<FuncionarioResponse>> buscarListaDeFuncionarios(@PathVariable Status status) {
        List<FuncionarioResponse> listaDeFuncionariosResponse = this.funcionarioService.buscarListaDeFuncionarios(status);

        return ResponseEntity.ok(listaDeFuncionariosResponse);
    }

    @GetMapping("/gerar-relatorio")
    public ResponseEntity<byte[]> gerarRelatorioDeFuncionario(){
        return this.funcionarioService.gerarRelatorioDeFuncionario();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastraFuncionario(@RequestBody CadastrarFuncionarioRequest cadastrarFuncionarioRequest) {
        this.funcionarioService.cadastraFuncionario(cadastrarFuncionarioRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarFuncionario(@PathVariable UUID id,
                                                     @RequestBody AtualizarFuncionarioRequest atualizarFuncionarioRequest) {
        this.funcionarioService.atualizarFuncionario(id, atualizarFuncionarioRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/atualizar-situacao/{id}")
    public ResponseEntity<Void> atualizarSituacaoFuncionario(@PathVariable UUID id) {
        this.funcionarioService.atualizarSituacaoFuncionario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/conceder-aumento/{id}")
    public ResponseEntity<Void> concederAumentoDeSalario(@PathVariable UUID id,
                                                         @RequestBody NovoSalarioRequest novoSalarioRequest) {
        this.funcionarioService.concederAumentoDeSalario(id, novoSalarioRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}