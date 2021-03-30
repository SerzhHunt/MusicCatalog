package com.epam.musiccatalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"authors"})
@ToString(exclude = {"authors"})
public class SongDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Duration duration;
    private List<AuthorDto> authors = new ArrayList<>();
    @JsonIgnore
    private AlbumDto album;
}
