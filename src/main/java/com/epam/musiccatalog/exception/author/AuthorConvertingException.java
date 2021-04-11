package com.epam.musiccatalog.exception.author;

public class AuthorConvertingException extends Exception {
    public AuthorConvertingException() {
    }

    public AuthorConvertingException(String message) {
        super(message);
    }

    public AuthorConvertingException(String message, Throwable cause) {
        super(message, cause);
    }
}
