package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.SongDto;

import java.util.List;

public interface SongService {

    List<SongDto> getAll();

    SongDto getSongById(String albumName, Long songId);

    SongDto save(String albumName, SongDto songDto);

    SongDto update(Long songId, SongDto songDto);

    void delete(Long songId);
}
