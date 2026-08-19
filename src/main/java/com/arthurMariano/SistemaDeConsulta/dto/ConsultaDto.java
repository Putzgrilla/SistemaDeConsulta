package com.arthurMariano.SistemaDeConsulta.dto;

import com.arthurMariano.SistemaDeConsulta.model.enums.Status;

import java.time.LocalDateTime;

public record ConsultaDto(Long id, MedicoPesquisaDto medico, PacienteDto paciente, LocalDateTime data, Status status) {


}
