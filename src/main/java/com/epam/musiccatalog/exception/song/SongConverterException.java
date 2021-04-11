package com.epam.musiccatalog.exception.song;

public class SongConverterException extends Exception {
    public SongConverterException() {
    }

    public SongConverterException(String message) {
        super(message);
    }

    public SongConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
