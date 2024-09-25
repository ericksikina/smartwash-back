package com.smartwash.application.movimentacaoEstoque.repository;

import com.smartwash.application.movimentacaoEstoque.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, UUID> {
    List<MovimentacaoEstoque> findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(LocalDateTime dataInicial, LocalDateTime dataFinal);
}