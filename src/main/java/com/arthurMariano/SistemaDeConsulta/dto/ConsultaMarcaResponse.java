package com.arthurMariano.SistemaDeConsulta.dto;

import java.time.LocalDateTime;

public record ConsultaMarcaResponse(Long id, String medico, String paciente, LocalDateTime data) {


}
