package com.epam.musiccatalog.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Author")
@Table(name = "author")
@Data
@EqualsAndHashCode(callSuper = false)
public class Author extends AbstractEntity{

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "song_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_song_id"))
    private List<Song> songs = new ArrayList<>();
}
