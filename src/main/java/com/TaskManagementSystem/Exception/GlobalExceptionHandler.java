package com.TaskManagementSystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ================= VALIDATION ERRORS =================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", "VALIDATION_FAILED",
                        "errors", errors
                ));
    }

    // ================= ENUM / JSON PARSING ERRORS =================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonParseException(HttpMessageNotReadableException ex) {

        Map<String, String> errors = new HashMap<>();

        Throwable root = ex.getMostSpecificCause();

        if (root.getMessage().contains("TaskStatus")) {
            errors.put("status", "Invalid status");
        }

        if (root.getMessage().contains("Priority")) {
            errors.put("priority", "Invalid priority");
        }

        return ResponseEntity.badRequest().body(Map.of(
                "message", "INVALID_REQUEST",
                "errors", errors
        ));
    }
    // ================= BUSINESS LOGIC ERRORS =================
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

            case "PROJECT_NOT_FOUND" -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "PROJECT_NOT_FOUND"));

            case "TASK_NOT_FOUND" -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "TASK_NOT_FOUND"));

            case "ACCESS_DENIED" -> ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "ACCESS_DENIED"));

            default -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", message)); // fallback (IMPORTANT)
        };
    }
}