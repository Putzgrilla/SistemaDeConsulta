package com.arthurMariano.SistemaDeConsulta.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Paciente extends Usuario {
    @Column(nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false, length = 100)
    private String telefone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Endereco")
    private Endereco endereco;
}
