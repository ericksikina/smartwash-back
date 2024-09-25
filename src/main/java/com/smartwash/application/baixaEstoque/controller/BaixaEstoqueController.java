package com.smartwash.application.baixaEstoque.controller;

import com.smartwash.application.baixaEstoque.mapper.BaixaEstoqueResponseMapper;
import com.smartwash.application.baixaEstoque.model.BaixaEstoque;
import com.smartwash.application.baixaEstoque.request.BaixaEstoqueRequest;
import com.smartwash.application.baixaEstoque.response.BaixaEstoqueResponse;
import com.smartwash.application.baixaEstoque.service.BaixaEstoqueService;
import com.smartwash.application.utils.request.DataRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/baixa-estoque")
public class BaixaEstoqueController {
    private final BaixaEstoqueService baixaEstoqueService;

    @Autowired
    public BaixaEstoqueController(BaixaEstoqueService baixaEstoqueService) {
        this.baixaEstoqueService = baixaEstoqueService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<BaixaEstoqueResponse>> buscarListaDeBaixaEstoque(){
        List<BaixaEstoque> listaDeBaixaEstoque = this.baixaEstoqueService.buscarListaDeBaixaEstoque();

        return ResponseEntity.ok(BaixaEstoqueResponseMapper.toBaixaEstoqueResponseList(listaDeBaixaEstoque));
    }

    @PostMapping("/filtrar-por-data")
    public ResponseEntity<List<BaixaEstoqueResponse>> buscarListaDeBaixaEstoqueFiltradoPorData(@RequestBody DataRequest dataRequest){
        List<BaixaEstoque> listaDeBaixaEstoque = this.baixaEstoqueService.buscarListaDeBaixaEstoqueFiltradoPorData(dataRequest);

        return ResponseEntity.ok(BaixaEstoqueResponseMapper.toBaixaEstoqueResponseList(listaDeBaixaEstoque));
    }

    @PostMapping("/filtrar-por-funcionario/{nome}")
    public ResponseEntity<List<BaixaEstoqueResponse>> buscarListaDeBaixaEstoqueFiltradoPorFuncionario(@PathVariable String nome){
        List<BaixaEstoque> listaDeBaixaEstoque = this.baixaEstoqueService.buscarListaDeBaixaEstoqueFiltradoPorFuncionario(nome);

        return ResponseEntity.ok(BaixaEstoqueResponseMapper.toBaixaEstoqueResponseList(listaDeBaixaEstoque));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarBaixaEstoque(@RequestBody BaixaEstoqueRequest baixaEstoqueRequest,
                                                      HttpServletRequest httpServletRequest){
        this.baixaEstoqueService.cadastrarBaixaEstoque(baixaEstoqueRequest, httpServletRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}