package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.Album;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends CommonRepository<Album> {

    Optional<Album> findAlbumByName(String name);
}
