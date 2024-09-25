package com.smartwash.application.movimentacaoCaixa.repository;

import com.smartwash.application.movimentacaoCaixa.model.MovimentacaoCaixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MovimentacaoCaixaRepository extends JpaRepository<MovimentacaoCaixa, UUID> {
    List<MovimentacaoCaixa> findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(LocalDateTime dataInicial, LocalDateTime dataFinal);
}