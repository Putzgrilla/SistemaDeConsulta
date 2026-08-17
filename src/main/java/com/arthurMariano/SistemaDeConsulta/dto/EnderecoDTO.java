package com.arthurMariano.SistemaDeConsulta.dto;



public record EnderecoDTO(
        String logradouro,
        String numero,
        String cep,
        String municipio,
        String estado
) {
}