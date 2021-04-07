package com.epam.musiccatalog.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Duration;

@Data
public class SongDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Duration duration;
    private AuthorDto author;
    private AlbumDto album;
}
