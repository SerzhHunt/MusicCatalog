package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.repository.SongRepository;
import com.epam.musiccatalog.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Override
    public List<SongDto> getAll() {
        return null;
    }

    @Override
    public SongDto getSongById(Long albumId, Long songId) {
        return null;
    }

    @Override
    public SongDto save(Long albumId, SongDto songDto) {
        return null;
    }

    @Override
    public SongDto update(Long albumId, SongDto songDto) {
        return null;
    }

    @Override
    public void delete(Long albumId, Long songId) {

    }
}
