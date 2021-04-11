package com.epam.musiccatalog.exception.album;

public class AlbumConverterException extends Exception {
    public AlbumConverterException() {
    }

    public AlbumConverterException(String message) {
        super(message);
    }

    public AlbumConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
