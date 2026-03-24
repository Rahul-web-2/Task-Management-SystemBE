package com.TaskManagementSystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {

        String message = ex.getMessage();

        return switch (message) {
            case "USER_NOT_FOUND" -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "USER_NOT_FOUND"));
            case "INVALID_CREDENTIALS" -> ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "INVALID_CREDENTIALS"));
            case "USER_ALREADY_EXISTS" -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "USER_ALREADY_EXISTS"));
            default -> ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "SERVER_ERROR"));
        };
    }
}