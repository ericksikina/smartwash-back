package com.smartwash.application.baixaEstoque.model;

import com.smartwash.application.produto.model.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "baixa_estoque_detalhe")
public class BaixaEstoqueDetalhe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="id_baixa_estoque", referencedColumnName="id")
    private BaixaEstoque baixaEstoque;

    @ManyToOne
    @JoinColumn(name="id_produto", referencedColumnName="id")
    private Produto produto;

    private Long quantidade;

    public BaixaEstoqueDetalhe(BaixaEstoque baixaEstoque, Produto produto, Long quantidade) {
        this.baixaEstoque = baixaEstoque;
        this.produto = produto;
        this.quantidade = quantidade;
    }
}