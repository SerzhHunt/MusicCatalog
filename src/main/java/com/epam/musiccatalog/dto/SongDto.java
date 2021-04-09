package com.epam.musiccatalog.dto;

import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
public class SongDto {

    private String name;
    private Duration duration;
    private List<String> authorNames = new ArrayList<>();
    private String albumName;
}
