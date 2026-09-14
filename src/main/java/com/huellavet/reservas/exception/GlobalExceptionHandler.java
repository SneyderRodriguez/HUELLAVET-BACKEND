package com.huellavet.reservas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CorreoYaRegistradoException.class)
    public ResponseEntity<Map<String, String>> manejarCorreoDuplicado(CorreoYaRegistradoException exception) {
        return crearRespuesta(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, String>> manejarCredencialesInvalidas(CredencialesInvalidasException exception) {
        return crearRespuesta(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException exception) {
        Map<String, String> errores = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler(AccesoNoAutorizadoException.class)
    public ResponseEntity<Map<String, String>> manejarAccesoNoAutorizado(AccesoNoAutorizadoException exception) {
        return crearRespuesta(HttpStatus.FORBIDDEN, exception.getMessage());
    }
    private  ResponseEntity<Map<String, String>> crearRespuesta(HttpStatus estado, String mensaje) {

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", mensaje);
        return ResponseEntity.status(estado).body(respuesta);
    }
}
