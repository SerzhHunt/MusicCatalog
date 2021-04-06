package com.epam.musiccatalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AlbumDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Duration duration;
    private List<AuthorDto> authors = new ArrayList<>();
    private List<SongDto> songs = new ArrayList<>();
    private LocalDate createdDate;
}
