package com.smartwash.application.funcionario.repository;

import com.smartwash.application.auth.model.Auth;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.utils.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    Funcionario findByAuth(Auth auth);
    Optional<Funcionario> findByCpf(String cpf);
    Optional<Funcionario> findByAuth_Login(String email);
    List<Funcionario> findAllByStatusOrderByNomeAsc(Status status);
}