package com.smartwash.application.pedido.controller;

import com.smartwash.application.pagamento.request.PagamentoRequest;
import com.smartwash.application.pedido.enums.StatusPedido;
import com.smartwash.application.pedido.request.PedidoRequest;
import com.smartwash.application.pedido.request.PedidoServicoRequest;
import com.smartwash.application.pedido.response.PedidoResponse;
import com.smartwash.application.pedido.service.PedidoService;
import com.smartwash.application.utils.request.DataRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidoResponse>> buscarListaDePedidos(){
        List<PedidoResponse> listaDePedidoResponse = this.pedidoService.buscarListaDePedidos();

        return ResponseEntity.ok(listaDePedidoResponse);
    }

    @PostMapping("/gerar-relatorio")
    public ResponseEntity<byte[]> gerarRelatorioDePedido(
            @RequestBody DataRequest dataRequest){
        return this.pedidoService.gerarRelatorioDePedido(dataRequest);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarPedido(@RequestBody PedidoRequest pedidoRequest,
                                                HttpServletRequest httpServletRequest){
        this.pedidoService.cadastrarPedido(pedidoRequest, httpServletRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar-status/{id}/{status}")
    public ResponseEntity<Void> atualizarStatus(@PathVariable UUID id, @PathVariable StatusPedido status){
        this.pedidoService.atualizarStatus(id, status);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/efetuar-pagamento/{id}")
    public ResponseEntity<Void> adiocionarPagamento(@PathVariable UUID id, @RequestBody PagamentoRequest pagamento){
        this.pedidoService.adiocionarPagamento(id, pagamento);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/calcular-total")
    public ResponseEntity<String> cacularValorTotalPedido(@RequestBody List<PedidoServicoRequest> listaDeServicos){
        return ResponseEntity.ok(this.pedidoService.cacularValorTotalPedido(listaDeServicos));
    }

    @GetMapping("/notificar/{idPedido}")
    public ResponseEntity<Void> notificarPedidoConcluido(@PathVariable UUID idPedido){
        this.pedidoService.enviarEmail(idPedido);
        return ResponseEntity.ok().build();
    }
}