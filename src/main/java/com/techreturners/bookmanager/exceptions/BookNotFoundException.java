package com.techreturners.bookmanager.exceptions;

public class BookNotFoundException extends RuntimeException {
    private String message;
    public BookNotFoundException (String message) {
        super (message);
        this.message = message;
    }
}
