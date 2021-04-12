package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.exception.album.AlbumConverterException;
import com.epam.musiccatalog.exception.album.AlbumException;
import com.epam.musiccatalog.exception.album.AlbumNotFoundException;
import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final MapperFacade mapper;

    @Override
    @Transactional
    public List<AlbumDto> getAll() throws AlbumException {
        try {
            List<AlbumDto> albums = new ArrayList<>();
            for (Album album : albumRepository.findAll()) {
                albums.add(albumToDto(album));
            }
            return albums;
        } catch (Exception e) {
            String message = "exception while getting all albums";
            log.error(message);
            throw new AlbumException(message, e);
        }
    }

    @Override
    @Transactional
    public AlbumDto getAlbumById(Long id) throws AlbumException {
        try {
            Optional<Album> album = albumRepository.findById(id);

            if (album.isEmpty()) {
                log.error("author not found by id {} ...", id);
                throw new AlbumNotFoundException(id);
            }
            return albumToDto(album.get());
        } catch (Exception e) {
            String message = "exception while getting album from db";
            log.error(message, e.getMessage());
            throw new AlbumException(message, e);
        }
    }

    @Override
    @Transactional
    public AlbumDto save(AlbumDto albumDto) throws AlbumException {
        try {
            Album savedAlbum = albumRepository.save(dtoToAuthor(albumDto));
            savedAlbum.setCreatedDate(LocalDate.now());
            return mapper.map(savedAlbum, AlbumDto.class);
        } catch (Exception e) {
            String message = "exception while save album in db";
            log.error(message, e.getMessage());
            throw new AlbumException(message, e);
        }
    }

    @Override
    @Transactional
    public AlbumDto update(Long id, AlbumDto albumDto) throws AlbumException {
        try {
            Optional<Album> albumById = albumRepository.findById(id);

            if (albumById.isEmpty()) {
                log.error("album not found by id {} ...", id);
                throw new AlbumNotFoundException(id);
            }
            Album updatedAlbum = albumRepository.save(updateAlbum(albumById.get(), albumDto));
            return albumToDto(updatedAlbum);
        } catch (Exception e) {
            String message = "exception while update album in db";
            log.error(message, e.getMessage());
            throw new AlbumException(message, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws AlbumException {
        try {
            albumRepository.deleteById(id);
        } catch (Exception e) {
            String message = "exception while delete album in db";
            log.error(message, e.getMessage());
            throw new AlbumException(message, e);
        }
    }

    private AlbumDto albumToDto(Album album) throws AlbumConverterException {
        if (Objects.isNull(album)) {
            throw new AlbumConverterException();
        }
        try {
            AlbumDto albumDto = mapper.map(album, AlbumDto.class);
            albumDto.setDuration(getSumSongsDurationFromAlbum(album));
            albumDto.setSongNames(getSongNamesOfAlbum(album));
            return albumDto;
        } catch (Exception e) {
            String message = "error occurred during converting from entity to dto";
            log.error(message, e.getMessage());
            throw new AlbumConverterException(message, e);
        }
    }

    private Album dtoToAuthor(AlbumDto albumDto) throws AlbumConverterException {
        if (Objects.isNull(albumDto)) {
            throw new AlbumConverterException();
        }
        try {
            return mapper.map(albumDto, Album.class);
        } catch (Exception e) {
            String message = "error occurred during converting from dto to entity";
            log.error(message, e.getMessage());
            throw new AlbumConverterException(message, e);
        }
    }

    private Album updateAlbum(Album album, AlbumDto albumDto) throws AlbumException {
        if (Objects.isNull(albumDto)) {
            String message = "album dto is null";
            log.error(message);
            throw new AlbumException(message);
        }
        album.setId(albumDto.getId());
        album.setName(albumDto.getName());
        return album;
    }

    private Duration getSumSongsDurationFromAlbum(Album album) {
        return Duration.ofMinutes(album.getSongs()
                .stream()
                .map(Song::getDuration)
                .count());
    }

    private List<String> getSongNamesOfAlbum(Album album) {
        return album.getSongs()
                .stream()
                .map(Song::getName)
                .collect(Collectors.toList());
    }
}
