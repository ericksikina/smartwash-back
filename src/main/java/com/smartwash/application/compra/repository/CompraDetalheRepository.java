package com.smartwash.application.compra.repository;

import com.smartwash.application.compra.model.CompraDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompraDetalheRepository extends JpaRepository<CompraDetalhe, UUID> {
}
