package com.appteam.template.controller;


import com.appteam.template.exception.AppError;
import com.appteam.template.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFound(RuntimeException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                                            e.getMessage()),
                                            HttpStatus.NOT_FOUND);

    }
}
