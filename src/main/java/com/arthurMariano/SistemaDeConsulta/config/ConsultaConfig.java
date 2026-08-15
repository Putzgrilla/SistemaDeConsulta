package com.arthurMariano.SistemaDeConsulta.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "consulta")

public record ConsultaConfig(Integer maximoDeConsultas, Integer minimoDeConsultas) {
}
