package com.epam.musiccatalog.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AuthorAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AlbumEntity album;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthorEntity author;
}
