package com.arthurMariano.SistemaDeConsulta.dto;



public record EnderecoDto(
        String logradouro,
        String numero,
        String cep,
        String municipio,
        String estado
) {
}