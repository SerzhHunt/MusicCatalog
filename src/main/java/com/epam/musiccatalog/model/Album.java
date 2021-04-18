package com.epam.musiccatalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Album")
@Table(name = "album")
@Data
@EqualsAndHashCode(callSuper = false)
public class Album extends AbstractEntity {

    @Column(name = "album_name", nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @OneToMany(mappedBy = "album",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();
}
