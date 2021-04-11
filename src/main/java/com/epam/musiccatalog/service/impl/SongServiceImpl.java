package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.exception.album.AlbumNotFoundException;
import com.epam.musiccatalog.exception.song.SongConverterException;
import com.epam.musiccatalog.exception.song.SongException;
import com.epam.musiccatalog.exception.song.SongNotFoundException;
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
    @Transactional
    public List<SongDto> getAll() throws SongException {
        try {
            List<SongDto> songs = new ArrayList<>();
            for (Song song : songRepository.findAll()) {
                songs.add(songToDto(song));
            }
            return songs;
        } catch (Exception e) {
            String message = "exception while getting all songs";
            log.error(message);
            throw new SongException(message, e);
        }
    }

    @Override
    @Transactional
    public SongDto getSongById(Long albumId, Long songId) throws SongException {
        try {
            Optional<Song> song = songRepository.findSongByIdInAlbumById(albumId, songId);

            if (song.isEmpty()) {
                log.error("song not found by id {} ...", songId);
                throw new SongNotFoundException(songId);
            }
            return songToDto(song.get());
        } catch (Exception e) {
            String message = "exception while getting song from db";
            log.error(message, e.getMessage());
            throw new SongException(message, e);
        }
    }

    @Override
    @Transactional
    public SongDto save(Long albumId, SongDto songDto) throws SongException {
        try {
            Optional<Album> album = albumRepository.findById(albumId);
            if (album.isEmpty()) {
                log.error("album not found by id {} ...", albumId);
                throw new AlbumNotFoundException(albumId);
            }
            Song savedSong = songRepository.save(dtoToSong(songDto));
            return songToDto(savedSong);
        } catch (Exception e) {
            String message = "exception while save song in db";
            log.error(message, e.getMessage());
            throw new SongException(message, e);
        }
    }

    @Override
    @Transactional
    public SongDto update(Long id, SongDto songDto) throws SongException {
        try {
            Optional<Song> song = songRepository.findById(id);

            if (song.isEmpty()) {
                log.error("song not found by id {} ...", id);
                throw new SongNotFoundException(id);
            }
            Song updatedSong = songRepository.save(updateSong(song.get(), songDto));
            return songToDto(updatedSong);
        } catch (Exception e) {
            String message = "exception while update song in db";
            log.error(message, e.getMessage());
            throw new SongException(message, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws SongException {
        try {
            songRepository.deleteById(id);
        } catch (Exception e) {
            String message = "exception while delete author in db";
            log.error(message, e.getMessage());
            throw new SongException(message, e);
        }
    }

    private SongDto songToDto(Song song) throws SongConverterException {
        if (Objects.isNull(song)) {
            throw new SongConverterException();
        }
        try {
            SongDto songDto = mapper.map(song, SongDto.class);
            songDto.setAuthorNames(getAuthorNamesOfSong(song));
            songDto.setAlbumName(getAlbumNameOfSong(song));
            return songDto;
        } catch (Exception e) {
            String message = "error occurred during converting from entity to dto";
            log.error(message, e.getMessage());
            throw new SongConverterException(message, e);
        }
    }

    private Song dtoToSong(SongDto songDto) throws SongConverterException {
        if (Objects.isNull(songDto)) {
            throw new SongConverterException();
        }
        try {
            return mapper.map(songDto, Song.class);
        } catch (Exception e) {
            String message = "error occurred during converting from dto to entity";
            log.error(message, e.getMessage());
            throw new SongConverterException(message, e);
        }
    }

    private Song updateSong(Song song, SongDto songDto) throws SongException {
        if (Objects.isNull(songDto)) {
            String message = "song dto is null";
            log.error(message);
            throw new SongException(message);
        }
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
