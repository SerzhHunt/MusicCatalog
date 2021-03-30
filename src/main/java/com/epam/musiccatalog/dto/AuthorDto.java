package com.epam.musiccatalog.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"albums"})
@ToString
public class AuthorDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private List<AlbumDto> albums = new ArrayList<>();
}
