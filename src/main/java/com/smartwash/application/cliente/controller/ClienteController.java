package com.smartwash.application.cliente.controller;

import com.smartwash.application.cliente.request.ClienteRequest;
import com.smartwash.application.cliente.response.ClienteResponse;
import com.smartwash.application.cliente.service.ClienteService;
import com.smartwash.application.utils.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/listar/{status}")
    public ResponseEntity<List<ClienteResponse>> buscarListaDeClientes(@PathVariable Status status){
        return ResponseEntity.ok(this.clienteService.buscarListaDeClientes(status));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarCliente(@RequestBody ClienteRequest clienteRequest){
        this.clienteService.cadastrarCliente(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarCliente(@PathVariable UUID id,
                                                 @RequestBody ClienteRequest clienteRequest){
        this.clienteService.atualizarCliente(id, clienteRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/atualizar-situacao/{id}")
    public ResponseEntity<Void> atualizarSituacaoCliente(@PathVariable UUID id){
        this.clienteService.atualizarSituacaoCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}