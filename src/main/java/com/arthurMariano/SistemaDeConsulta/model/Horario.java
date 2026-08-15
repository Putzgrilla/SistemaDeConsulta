package com.arthurMariano.SistemaDeConsulta.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    @ManyToOne
    @JoinColumn(name = "medico")
    private Medico medico;
    @Column(nullable = false)
    private LocalTime inicio;
    @Column(nullable = false)
    private LocalTime fim;
    @Column(nullable = false)
    private LocalTime intervalo;
    @Column(nullable = false)
    private LocalTime fimIntervalo;
    @Column(nullable = false)
    private boolean ativo;
}
