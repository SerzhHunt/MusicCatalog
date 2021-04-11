package com.epam.musiccatalog.exception.song;

public class SongNotFoundException extends Exception {
    public SongNotFoundException() {
    }

    public SongNotFoundException(String message) {
        super(message);
    }

    public SongNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SongNotFoundException(Long id) {
        super(String.format("song with id %s not found", id));
    }

    public SongNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
