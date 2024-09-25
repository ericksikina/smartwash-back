package com.smartwash.application.funcionario.model;

import com.smartwash.application.auth.model.Auth;
import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.utils.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String cpf;
    private String celular;
    private BigDecimal salario;
    @Column(name = "data_contratacao")
    private LocalDate dataContratacao;
    private Status status;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_auth")
    private Auth auth;
}
