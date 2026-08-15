package com.arthurMariano.SistemaDeConsulta.exception;


import lombok.Getter;

@Getter
public class DadoJaCadastradoException extends RuntimeException {
    private final String field;

    public DadoJaCadastradoException(String message, String field) {
        super(message);
        this.field = field;
    }
}
