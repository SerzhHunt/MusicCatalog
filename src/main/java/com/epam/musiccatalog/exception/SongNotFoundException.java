package com.epam.musiccatalog.exception;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(Long id) {
        super(String.format("Song id not found : %s", id));
    }
}
