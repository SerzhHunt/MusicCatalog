package com.epam.musiccatalog.exception.song;

public class SongException extends Exception {
    public SongException() {
    }

    public SongException(String message) {
        super(message);
    }

    public SongException(String message, Throwable cause) {
        super(message, cause);
    }
}
