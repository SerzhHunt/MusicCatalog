package com.epam.musiccatalog.exception.album;

public class AlbumNotFoundException extends Exception {

    public AlbumNotFoundException() {
    }

    public AlbumNotFoundException(String message) {
        super(message);
    }

    public AlbumNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlbumNotFoundException(Long id) {
        super(String.format("album with id %s not found", id));
    }

    public AlbumNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
