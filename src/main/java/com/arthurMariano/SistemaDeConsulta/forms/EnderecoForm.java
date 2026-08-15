package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoForm(
        @NotBlank @Size(max = 60) String logradouro,
        @NotBlank @Size(max = 9) String numero,
        @NotBlank @Size(max = 8, min = 8) String cep,
        @NotBlank @Size(max = 60) String municipio,
        @NotBlank @Size(max = 2, min = 2) String estado
) {
}