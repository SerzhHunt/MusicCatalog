package com.epam.musiccatalog.model;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;

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

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.REMOVE,
                    CascadeType.MERGE})
    @JoinColumn(name = "author_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_author_id"))
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.PERSIST})
    @JoinColumn(name = "album_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_album_id"))
    private Album album;
}
