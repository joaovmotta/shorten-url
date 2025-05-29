package br.com.shorten_url.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<?> handleHttpException(HttpException ex) {

        Map<String, Object> response = Map.of("code", ex.getCode(),
                "message", ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus())
                .body(response);
    }
}
