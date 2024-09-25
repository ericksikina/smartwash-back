package com.smartwash.application.compra.model;

import com.smartwash.application.produto.model.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "compra_detalhe")
public class CompraDetalhe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="id_compra", referencedColumnName="id")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name="id_produto", referencedColumnName="id")
    private Produto produto;

    private Long quantidade;
    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    public CompraDetalhe(Compra compra, Produto produto, BigDecimal precoUnitario, Long quantidade) {
        this.compra = compra;
        this.precoUnitario = precoUnitario;
        this.produto = produto;
        this.quantidade = quantidade;
    }
}