package com.smartwash.application.fornecedor.repository;

import com.smartwash.application.fornecedor.model.Fornecedor;
import com.smartwash.application.utils.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {
    List<Fornecedor> findAllByStatusOrderByDescricaoAsc(Status status);
}