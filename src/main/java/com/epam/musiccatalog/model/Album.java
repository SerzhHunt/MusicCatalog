package com.epam.musiccatalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity(name = "Album")
@Table(name = "album")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Album extends AbstractEntity {

    @Column(name = "album_name", nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Song> songs = new ArrayList<>();
}
