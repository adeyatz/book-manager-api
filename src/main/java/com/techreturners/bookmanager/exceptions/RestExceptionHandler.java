package com.techreturners.bookmanager.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {
    @ExceptionHandler({BookNotFoundException.class})
    protected ResponseEntity<Object> handleBookNotFoundException (BookNotFoundException exception, WebRequest request) {
        return handleExceptionInternal(exception,
                request.getDescription(false) + " : " +  exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);

//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementException (NoSuchElementException exception, WebRequest request) {
        return handleExceptionInternal(exception,
                request.getDescription(false) + " : " +  exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);

//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(exception.getMessage());
    }


}
