package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginForm(@Email @NotBlank @Size(max = 100, min = 7) String login,
                        @NotBlank @Size(max = 100, min = 7) String senha) {
}
