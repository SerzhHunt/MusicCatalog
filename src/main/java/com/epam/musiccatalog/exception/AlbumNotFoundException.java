package com.epam.musiccatalog.exception;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(Long id) {
        super(String.format("Album id not found : %s", id));
    }

    public AlbumNotFoundException(String albumName) {
        super(String.format("Album with name is \"%s\" not found!", albumName));
    }
}
