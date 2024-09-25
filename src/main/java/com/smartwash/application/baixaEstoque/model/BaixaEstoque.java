package com.smartwash.application.baixaEstoque.model;

import com.smartwash.application.funcionario.model.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "baixa_estoque")
public class BaixaEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
    @OneToMany(mappedBy = "baixaEstoque", cascade = CascadeType.PERSIST)
    private Set<BaixaEstoqueDetalhe> listaDeBaixaEstoque = new HashSet<>();
}
