package com.Java.demo.exception;

import com.Java.demo.exception.customException.ResourceExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<Map<String, Object>> handleResourceExistException(ResourceExistException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", "Resource already exists",
                        "message", ex.getMessage()
                ));
    }
}
