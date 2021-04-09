package com.epam.musiccatalog.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AlbumDto {

    private String name;
    private LocalDate createdDate;
    private Duration duration;
    private List<String> songNames = new ArrayList<>();
}
