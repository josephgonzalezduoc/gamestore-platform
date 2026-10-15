package com.gamestore.stock.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import feign.FeignException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // errores de validaciones (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    // JSON mal formado o parametro con tipo incorrecto
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Map<String, String>> manejarFormatoInvalido(Exception ex) {
        return respuesta(HttpStatus.BAD_REQUEST, "La solicitud tiene un formato inválido");
    }

    // reglas de negocio con datos incorrectos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return respuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // recurso no encontrado
    @ExceptionHandler({ResourceNotFoundException.class, NoSuchElementException.class, NoResourceFoundException.class})
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(Exception ex) {
        return respuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // conflicto con el estado actual (duplicados, sin stock, etc.)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> manejarConflicto(ConflictException ex) {
        return respuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    // error al llamar a otro microservicio (se respeta el codigo que respondio)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> manejarFeign(FeignException ex) {

        log.error("Error al comunicarse con otro microservicio: {}", ex.getMessage());

        HttpStatus status = ex.status() > 0 ? HttpStatus.resolve(ex.status()) : null;

        if (status == null) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }

        String detalle = ex.contentUTF8();

        String mensaje = "Error en un microservicio dependiente"
                + ((detalle == null || detalle.isBlank()) ? "" : ": " + detalle);

        return respuesta(status, mensaje);
    }

    // errores generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrores(Exception ex) {
        log.error("Error no controlado", ex);
        return respuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    private ResponseEntity<Map<String, String>> respuesta(HttpStatus status, String mensaje) {
        return new ResponseEntity<>(Map.of("error", String.valueOf(mensaje)), status);
    }

}
