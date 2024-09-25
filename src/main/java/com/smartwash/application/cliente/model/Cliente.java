package com.smartwash.application.cliente.model;

import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.utils.enums.Resposta;
import com.smartwash.application.utils.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String cpf;
    private String celular;
    private String email;
    @Column(name = "deseja_ser_notificado")
    private Resposta desejaSerNotificado;
    private Status status;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
}
