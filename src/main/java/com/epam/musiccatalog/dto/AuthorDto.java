package com.epam.musiccatalog.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private List<SongDto> songs = new ArrayList<>();
}
