package com.example.blog.handler;

import com.example.blog.dto.CommonResponseDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String handleArgumentException(IllegalArgumentException e) {
        return "<h1>" + e.getMessage() + "<h1>";
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public CommonResponseDto<?> handleDataException(DataIntegrityViolationException e) {
        return new CommonResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public String test(ConstraintViolationException e) {
        return "<h3>" + e.getMessage() + "<h3>";
    }

}
