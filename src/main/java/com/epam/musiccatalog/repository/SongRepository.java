package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.Song;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CommonRepository<Song> {
}
