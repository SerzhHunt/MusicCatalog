package com.epam.musiccatalog.exception.author;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException() {
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorNotFoundException(Throwable cause) {
        super(cause);
    }

    public AuthorNotFoundException(Long id) {
        super(String.format("author with id %s not found", id));
    }

    protected AuthorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
