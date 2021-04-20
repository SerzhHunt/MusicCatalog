package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.AlbumDto;

import java.util.List;

public interface AlbumService {
    List<AlbumDto> getAll();

    AlbumDto getAlbumById(Long id);

    AlbumDto save(AlbumDto albumDto);

    AlbumDto update(Long id, AlbumDto albumDto);

    void delete(Long id);
}
