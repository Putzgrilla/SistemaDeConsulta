package com.arthurMariano.SistemaDeConsulta.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(nullable = false, length = 60)
    private String logradouro;
    @Column(nullable = false, length = 9)
    private String numero;
    @Column(nullable = false, length = 8)
    private String cep;
    @Column(nullable = false, length = 60)
    private String municipio;
    @Column(nullable = false, length = 2)
    private String estado;

}
