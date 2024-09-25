package com.smartwash.application.movimentacaoCaixa.model;

import com.smartwash.application.compra.model.Compra;
import com.smartwash.application.movimentacaoEstoque.enums.TipoMovimentacao;
import com.smartwash.application.pedido.model.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimentacao_caixa")
public class MovimentacaoCaixa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    @Column(name = "tipo_movimentacao")
    private TipoMovimentacao tipoMovimentacao;
    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    @OneToOne
    @JoinColumn(name = "id_compra")
    private Compra compra;
}
