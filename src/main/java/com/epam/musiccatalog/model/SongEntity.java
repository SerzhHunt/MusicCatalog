package com.epam.musiccatalog.model;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity(name = "Song")
@Table(name = "song")
@Data
public class SongEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Duration duration;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private List<AuthorEntity> authors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.PERSIST})
    @JoinColumn(name = "album_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_album_id"))
    private AlbumEntity album;
}
