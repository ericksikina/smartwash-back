package com.smartwash.application.baixaEstoque.repository;

import com.smartwash.application.baixaEstoque.model.BaixaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BaixaEstoqueRepository extends JpaRepository<BaixaEstoque, UUID> {
    List<BaixaEstoque> findAllByOrderByDataHoraDesc();
    List<BaixaEstoque> findAllByFuncionario_NomeContainingIgnoreCase(String nome);
    List<BaixaEstoque> findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(LocalDateTime dataInicial, LocalDateTime dataFinal);
}