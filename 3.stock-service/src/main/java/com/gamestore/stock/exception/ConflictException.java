package com.gamestore.stock.exception;

// Se lanza cuando la operacion choca con el estado actual (responde 409)
public class ConflictException extends RuntimeException {

    public ConflictException(String mensaje) {
        super(mensaje);
    }
}
