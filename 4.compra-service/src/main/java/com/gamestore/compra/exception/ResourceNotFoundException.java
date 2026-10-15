package com.gamestore.compra.exception;

// Se lanza cuando un recurso no existe (responde 404)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
