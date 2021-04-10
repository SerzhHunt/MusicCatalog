package com.epam.musiccatalog.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDto {

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private List<String> songNames = new ArrayList<>();
}
