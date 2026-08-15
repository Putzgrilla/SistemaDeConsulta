package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicoForm(
        @NotNull Boolean admin,
        @NotBlank @Size(max = 100, min = 7) String nome,
        @NotBlank @Size(max = 100, min = 7) String senha,
        @NotNull Long especialidadeID,
        @NotBlank @Email String login, @NotBlank String consultorio) {
}
