package com.arthurMariano.SistemaDeConsulta.config;

import com.arthurMariano.SistemaDeConsulta.model.enums.Cargo;
import lombok.Builder;


@Builder
public record JwtUserData(Long id, String email, Cargo cargo) {

}
