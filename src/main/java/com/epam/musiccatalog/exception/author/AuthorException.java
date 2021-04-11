package com.epam.musiccatalog.exception.author;


public class AuthorException extends Exception {
    public AuthorException() {
    }

    public AuthorException(String message) {
        super(message);
    }

    public AuthorException(String message, Throwable cause) {
        super(message, cause);
    }
}
