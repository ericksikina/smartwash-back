package com.smartwash.application.produto.model;

import com.smartwash.application.utils.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String descricao;
    @Column(name = "quantidade_estoque")
    private int quantidadeEstoque;
    @Column(name = "estoque_minimo")
    private int estoqueMinimo;
    private Status status;
}
