package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ConsultaForm(
        @NotNull LocalDateTime data,
        @NotBlank Long medicoId,
        @NotNull Long pacienteId,
        @NotNull int tempo,
        @NotBlank @Size(min = 10, max = 500) String descricao
) {
}