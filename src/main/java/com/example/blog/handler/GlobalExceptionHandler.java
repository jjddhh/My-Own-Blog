package com.example.blog.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String handleArgumentException(IllegalArgumentException e) {
        return "<h1>" + e.getMessage() + "<h1>";
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public String handleDataException(DataIntegrityViolationException e) {
        return "<h1>" + e.getMessage() + "<h1>";
    }
}
