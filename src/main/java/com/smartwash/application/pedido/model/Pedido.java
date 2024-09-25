package com.smartwash.application.pedido.model;

import com.smartwash.application.cliente.model.Cliente;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.pagamento.model.Pagamento;
import com.smartwash.application.pedido.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_pagamento")
    private Pagamento pagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<PedidoDetalhe> listaDeServicos = new ArrayList<>();
}