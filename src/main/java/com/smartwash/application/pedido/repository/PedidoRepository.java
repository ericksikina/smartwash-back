package com.smartwash.application.pedido.repository;

import com.smartwash.application.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findAllByOrderByDataHoraDesc();
    List<Pedido> findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(LocalDateTime dataInicial, LocalDateTime dataFinal);
}