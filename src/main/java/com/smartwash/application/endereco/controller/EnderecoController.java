package com.smartwash.application.endereco.controller;

import com.smartwash.application.endereco.response.EnderecoViaCepResponse;
import com.smartwash.application.endereco.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/buscar-dados-endereco/{cep}")
    public ResponseEntity<EnderecoViaCepResponse> buscarDadosDeEnderecoPeloCep(@PathVariable String cep){
        EnderecoViaCepResponse viaCepResponse = this.enderecoService.buscarDadosDeEnderecoPeloCep(cep);

        return ResponseEntity.ok(viaCepResponse);
    }
}
