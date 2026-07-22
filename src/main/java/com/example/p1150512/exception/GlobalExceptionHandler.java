package com.example.p1150512.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> paramExceptionHandler(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(errorMessage);
    }
}