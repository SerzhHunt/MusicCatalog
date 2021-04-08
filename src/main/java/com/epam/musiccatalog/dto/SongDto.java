package com.epam.musiccatalog.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
public class SongDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Duration duration;
    private List<AuthorDto> authors = new ArrayList<>();
    private AlbumDto albumDto;
}
