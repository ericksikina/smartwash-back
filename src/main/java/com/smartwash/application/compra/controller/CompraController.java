package com.smartwash.application.compra.controller;

import com.smartwash.application.compra.mapper.CompraResponseMapper;
import com.smartwash.application.compra.model.Compra;
import com.smartwash.application.compra.request.CompraProdutoRequest;
import com.smartwash.application.compra.request.CompraRequest;
import com.smartwash.application.compra.response.CompraResponse;
import com.smartwash.application.compra.service.CompraService;
import com.smartwash.application.pedido.request.PedidoServicoRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/compra")
public class CompraController {
    private final CompraService compraService;

    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CompraResponse>> buscarListaDeCompra(){
        List<Compra> listaDeCompra = this.compraService. buscarListaDeCompra();

        return ResponseEntity.ok(CompraResponseMapper.toCompraResponseList(listaDeCompra));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarCompra(@RequestBody CompraRequest compraRequest, HttpServletRequest httpServletRequestrequest){
        this.compraService.cadastrarCompra(compraRequest, httpServletRequestrequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/calcular-total")
    public ResponseEntity<String> cacularValorTotalPedido(@RequestBody List<CompraProdutoRequest> listaDeProdutos){
        return ResponseEntity.ok(this.compraService.cacularValorTotalCompra(listaDeProdutos));
    }
}