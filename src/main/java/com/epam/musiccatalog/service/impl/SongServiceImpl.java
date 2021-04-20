package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.exception.AlbumNotFoundException;
import com.epam.musiccatalog.exception.SongNotFoundException;
import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.repository.SongRepository;
import com.epam.musiccatalog.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final MapperFacade mapper;

    @Override
    public List<SongDto> getAll() {
        return mapper.mapAsList(songRepository.findAll(), SongDto.class);
    }

    @Override
    public SongDto getSongById(Long albumId, Long songId) {
        Optional<Album> album = albumRepository.findById(albumId);
        Optional<Song> song = songRepository.findSongByIdInAlbumById(albumId, songId);

        if (album.isEmpty()) {
            log.error("album not found by id {} ...", albumId);
            throw new AlbumNotFoundException(albumId);
        }

        if (song.isEmpty()) {
            log.error("song not found by id {} ...", songId);
            throw new SongNotFoundException(songId);
        }
        return songToDto(song.get());
    }

    @Override
    @Transactional
    public SongDto save(Long albumId, SongDto songDto) {
        Optional<Album> album = albumRepository.findById(albumId);

        if (album.isEmpty()) {
            log.error("album not found by id {} ...", albumId);
            throw new AlbumNotFoundException(albumId);
        }

        Song savedSong = songRepository.save(mapper.map(songDto, Song.class));
        return songToDto(savedSong);
    }

    @Override
    @Transactional
    public SongDto update(Long id, SongDto songDto) {
        Optional<Song> song = songRepository.findById(id);

        if (song.isEmpty()) {
            log.error("song not found by id {} ...", id);
            throw new SongNotFoundException(id);
        }

        Song updatedSong = songRepository.save(updateSong(song.get(), songDto));
        return songToDto(updatedSong);
    }

    @Override
    public void delete(Long id) {
        songRepository.deleteById(id);
    }

    private SongDto songToDto(Song song) {
        SongDto songDto = mapper.map(song, SongDto.class);
        songDto.setAuthorNames(getAuthorNamesOfSong(song));
        songDto.setAlbumName(getAlbumNameOfSong(song));
        return songDto;
    }

    private Song updateSong(Song song, SongDto songDto) {
        song.setId(songDto.getId());
        song.setName(songDto.getName());
        song.setDuration(songDto.getDuration());
        return song;
    }

    private Set<String> getAuthorNamesOfSong(Song song) {
        return song.getAuthors()
                .stream()
                .map(author -> author.getFirstname().concat(" ").concat(author.getLastname()))
                .collect(Collectors.toSet());
    }

    private String getAlbumNameOfSong(Song song) {
        return song.getAlbum().getName();
    }
}
