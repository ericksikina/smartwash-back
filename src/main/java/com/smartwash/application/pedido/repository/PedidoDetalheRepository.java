package com.smartwash.application.pedido.repository;

import com.smartwash.application.pedido.model.PedidoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoDetalheRepository extends JpaRepository<PedidoDetalhe, UUID> {
}