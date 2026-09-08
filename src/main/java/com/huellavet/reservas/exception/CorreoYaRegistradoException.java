package com.huellavet.reservas.exception;

public class CorreoYaRegistradoException extends RuntimeException {
    public CorreoYaRegistradoException(String message) {
        super(message);
    }
}
