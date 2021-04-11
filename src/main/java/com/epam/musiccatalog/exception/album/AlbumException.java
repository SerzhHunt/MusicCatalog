package com.epam.musiccatalog.exception.album;

public class AlbumException extends Exception {
    public AlbumException() {
    }

    public AlbumException(String message) {
        super(message);
    }

    public AlbumException(String message, Throwable cause) {
        super(message, cause);
    }
}
