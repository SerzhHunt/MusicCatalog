package com.epam.musiccatalog.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long id) {
        super(String.format("Author id not found : %s", id));
    }
}
