package com.smartwash.application.produto.repository;

import com.smartwash.application.produto.model.Produto;
import com.smartwash.application.utils.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findAllByStatusOrderByDescricaoAsc(Status status);
    @Query("SELECT p FROM Produto p WHERE p.status = :status AND p.quantidadeEstoque < p.estoqueMinimo")
    List<Produto> findByStatusAndQuantidadeEstoqueLessThanEstoqueMinimo(Status status);
}