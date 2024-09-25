package com.smartwash.application.pedido.model;

import com.smartwash.application.servico.model.Servico;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "pedido_detalhe")
public class PedidoDetalhe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="id_pedido", referencedColumnName="id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name="id_servico", referencedColumnName="id")
    private Servico servico;

    private Long quantidade;

    public PedidoDetalhe(Pedido pedido, Servico servico, Long quantidade) {
        this.pedido = pedido;
        this.quantidade = quantidade;
        this.servico = servico;
    }
}