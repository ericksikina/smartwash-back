package com.smartwash.application.servico.repository;

import com.smartwash.application.servico.model.Servico;
import com.smartwash.application.utils.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, UUID> {
    List<Servico> findAllByStatusOrderByDescricaoAsc(Status status);
}