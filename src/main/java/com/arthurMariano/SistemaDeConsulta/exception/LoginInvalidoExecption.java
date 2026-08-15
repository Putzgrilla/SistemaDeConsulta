package com.arthurMariano.SistemaDeConsulta.exception;

import lombok.Getter;

@Getter
public class LoginInvalidoExecption extends RuntimeException {
    private final String field;

    public LoginInvalidoExecption(String mensagem) {
        super(mensagem);
        this.field = "Login";
    }
}
