package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<SongEntity,Long> {
}
