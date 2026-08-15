package com.arthurMariano.SistemaDeConsulta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErroResponse {
    private String field;
    private String mensagem;
    private int status;
    private LocalDateTime timestamp;
}
