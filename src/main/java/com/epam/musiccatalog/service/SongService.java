package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.exception.song.SongException;

import java.util.List;

public interface SongService {

    List<SongDto> getAll() throws SongException;

    SongDto getSongById(Long albumId, Long songId) throws SongException;

    SongDto save(Long albumId, SongDto songDto) throws SongException;

    SongDto update(Long songId, SongDto songDto) throws SongException;

    void delete(Long songId) throws SongException;
}
