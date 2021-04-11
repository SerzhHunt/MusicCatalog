package com.epam.musiccatalog.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
}
