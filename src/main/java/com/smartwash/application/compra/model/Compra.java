package com.smartwash.application.compra.model;

import com.smartwash.application.fornecedor.model.Fornecedor;
import com.smartwash.application.funcionario.model.Funcionario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    @OneToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
    @OneToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.PERSIST)
    private List<CompraDetalhe> listaDeCompra = new ArrayList<>();
}
