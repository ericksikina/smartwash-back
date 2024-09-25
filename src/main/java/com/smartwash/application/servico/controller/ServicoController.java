package com.smartwash.application.servico.controller;

import com.smartwash.application.servico.request.ServicoRequest;
import com.smartwash.application.servico.response.ServicoResponse;
import com.smartwash.application.servico.service.ServicoService;
import com.smartwash.application.utils.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servico")
public class ServicoController {
    private final ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping("/listar/{status}")
    public ResponseEntity<List<ServicoResponse>> buscarListaDeServicos(@PathVariable Status status) {
        List<ServicoResponse> listaDeServicoReponse = this.servicoService.buscarListaDeServicos(status);

        return ResponseEntity.ok(listaDeServicoReponse);
    }

    @GetMapping("/gerar-relatorio")
    public ResponseEntity<byte[]> gerarRelatorioDeServico(){
        return this.servicoService.gerarRelatorioDeServico();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarServico(@RequestBody ServicoRequest servicoRequest) {
        this.servicoService.cadastrarServico(servicoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarServico(@PathVariable UUID id,
                                                 @RequestBody ServicoRequest servicoRequest) {
        this.servicoService.atualizarServico(id, servicoRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/atualizar-situacao/{id}")
    public ResponseEntity<Void> atualizarSituacaoServico(@PathVariable UUID id) {
        this.servicoService.atualizarSituacaoServico(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}