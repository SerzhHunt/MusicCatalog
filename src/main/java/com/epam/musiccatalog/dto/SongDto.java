package com.epam.musiccatalog.dto;

import lombok.Data;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Data
public class SongDto {

    private Long id;
    private String name;
    private Duration duration;
    private Set<String> authorNames = new HashSet<>();
    private String albumName;
}
