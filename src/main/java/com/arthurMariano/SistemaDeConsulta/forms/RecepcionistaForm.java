package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecepcionistaForm(
        @NotNull Boolean admin,
        @NotBlank @Size(max = 100, min = 7) String nome,
        @NotBlank @Size(max = 100, min = 7) String senha,
        @NotBlank @Email String login) {
}
