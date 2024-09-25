package com.smartwash.application.baixaEstoque.repository;

import com.smartwash.application.baixaEstoque.model.BaixaEstoqueDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BaixaEstoqueDetalheRepository extends JpaRepository<BaixaEstoqueDetalhe, UUID> {
}