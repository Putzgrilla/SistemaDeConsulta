package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PacienteForm(@NotBlank @Size(max = 100, min = 7) String nome,
                           @NotBlank @Size(max = 100, min = 7) String senha,
                           @Email @NotBlank @Size(max = 100, min = 7) String login, @Valid EnderecoForm endereco,
                           @NotBlank @Size(max = 11, min = 11) String telefone,
                           @NotBlank @Size(max = 11, min = 11) String cpf) {
}
