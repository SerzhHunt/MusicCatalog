package com.epam.musiccatalog.model;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Album")
@Table(name = "album")
@Data
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Duration duration;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AuthorAlbum> authors = new ArrayList<>();

    @OneToMany(mappedBy = "album",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<SongEntity> songs = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;
}
