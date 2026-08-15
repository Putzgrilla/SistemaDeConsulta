package com.arthurMariano.SistemaDeConsulta.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Ausencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 200)
    private String motivo;
    @Column(nullable = false)
    private LocalDate inicio;
    @Column(nullable = false)
    private LocalDate fim;
    @JoinColumn(name = "medico_id")
    @ManyToOne
    private Medico medico;
}
