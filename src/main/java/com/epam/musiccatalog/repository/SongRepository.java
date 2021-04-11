package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends CommonRepository<Song> {

    @Query("SELECT s FROM Song s WHERE s.album.id = :albumId AND s.id = :songId")
    Optional<Song> findSongByIdInAlbumById(@Param("albumId") Long albumId,
                                           @Param("songId") Long songId);

}
