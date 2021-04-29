package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends CommonRepository<Album> {
    @Query("SELECT a FROM Album a WHERE UPPER(a.name) LIKE CONCAT('%',UPPER(:albumName),'%')")
    Optional<Album> findAlbumWithPartOfName(@Param("albumName") String albumName);
}
