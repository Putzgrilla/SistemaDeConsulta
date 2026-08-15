package com.arthurMariano.SistemaDeConsulta.exception;

import lombok.Getter;

@Getter
public class NaoAchadoException extends RuntimeException {
    private final String field;

    public NaoAchadoException(String mensagem, String field) {
        super(mensagem);
        this.field = field;
    }
}
