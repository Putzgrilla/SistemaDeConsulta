package com.arthurMariano.SistemaDeConsulta.exception;

public class DataInvalidaException extends RuntimeException {
    private final String field;

    public DataInvalidaException(String msg) {
        super(msg);
        this.field = "data";
    }
}
