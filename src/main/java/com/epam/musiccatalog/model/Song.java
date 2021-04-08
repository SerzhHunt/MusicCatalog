package com.epam.musiccatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity(name = "Song")
@Table(name = "song")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Duration duration;

    @ManyToMany(mappedBy = "songs", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Author> authors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.PERSIST})
    @JoinColumn(name = "album_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_album_id"))
    @JsonIgnoreProperties("songs")
    private Album album;
}
