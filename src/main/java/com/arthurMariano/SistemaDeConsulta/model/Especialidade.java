package com.arthurMariano.SistemaDeConsulta.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Especialidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 20)
    private String nome;
}
