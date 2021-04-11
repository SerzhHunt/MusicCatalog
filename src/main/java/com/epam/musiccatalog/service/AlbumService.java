package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.exception.album.AlbumException;

import java.util.List;

public interface AlbumService {
    List<AlbumDto> getAll() throws AlbumException;

    AlbumDto getAlbumById(Long id) throws AlbumException;

    AlbumDto save(AlbumDto albumDto) throws AlbumException;

    AlbumDto update(Long id, AlbumDto albumDto) throws AlbumException;

    void delete(Long id) throws AlbumException;
}
