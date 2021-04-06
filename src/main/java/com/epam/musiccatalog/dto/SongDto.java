package com.epam.musiccatalog.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
public class SongDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Duration duration;
    private List<AuthorDto> authors = new ArrayList<>();
    private AlbumDto album;
}
