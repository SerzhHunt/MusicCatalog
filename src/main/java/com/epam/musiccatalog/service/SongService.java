package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.SongDto;

import java.util.List;

public interface SongService {

    List<SongDto> getAll();

    SongDto getSongById(Long albumId, Long songId);

    SongDto save(Long albumId, SongDto songDto);

    SongDto update(Long albumId, SongDto songDto);

    void delete(Long albumId, Long songId);
}
