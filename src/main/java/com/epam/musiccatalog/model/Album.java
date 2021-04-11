package com.epam.musiccatalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Album")
@Table(name = "album")
@Data
@EqualsAndHashCode(callSuper = false)
public class Album extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @OneToMany(mappedBy = "album",
            cascade = {CascadeType.ALL, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();
}
