package com.arthurMariano.SistemaDeConsulta.model;

import com.arthurMariano.SistemaDeConsulta.model.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime data;
    @ManyToOne
    @JoinColumn(name = "medico")
    private Medico medico;
    @ManyToOne
    @JoinColumn(name = "paciente")
    private Paciente paciente;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private int tempo;
    @Column(nullable = false)
    private String descricao;
}
