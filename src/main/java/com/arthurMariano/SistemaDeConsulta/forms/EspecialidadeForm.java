package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EspecialidadeForm(@NotBlank @Size(max = 30, min = 5) String nome) {
}
