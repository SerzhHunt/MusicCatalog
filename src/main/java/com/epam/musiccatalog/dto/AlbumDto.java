package com.epam.musiccatalog.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AlbumDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private LocalDate createdDate;
    private Duration duration;
    private List<SongDto> songs = new ArrayList<>();
}
