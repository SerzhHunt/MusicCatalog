package com.epam.musiccatalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Song")
@Table(name = "song")
@Data
@EqualsAndHashCode(callSuper = false)
public class Song extends AbstractEntity {

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
    private Album album;
}
