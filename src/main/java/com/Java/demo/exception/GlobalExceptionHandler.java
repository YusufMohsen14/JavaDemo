package com.Java.demo.exception;

import com.Java.demo.exception.customException.InvalidTokenException;
import com.Java.demo.exception.customException.LoginAuthenticationException;
import com.Java.demo.exception.customException.ResourceExistException;
import com.Java.demo.exception.customException.ResourceNotFoundException;
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
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", "Resource already exists",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", "Resource not found",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(LoginAuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleLoginAuthenticationException(LoginAuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", HttpStatus.UNAUTHORIZED.value(),
                        "error", "Authentication failed",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", HttpStatus.UNAUTHORIZED.value(),
                        "error", "Invalid token",
                        "message", ex.getMessage()
                ));
    }
}
