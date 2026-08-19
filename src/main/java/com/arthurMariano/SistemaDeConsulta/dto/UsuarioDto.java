package com.arthurMariano.SistemaDeConsulta.dto;

import com.arthurMariano.SistemaDeConsulta.model.enums.Cargo;

public record UsuarioDto(
        Long id,
        String nome,
        String login,
        Cargo cargo,

        // campos exclusivos de Paciente (nulos para os outros cargos)
        String cpf,
        String telefone,
        EnderecoDto endereco,

        // campos exclusivos de Medico (nulos para os outros cargos)
        String consultorio,
        String especialidade
) {
}
