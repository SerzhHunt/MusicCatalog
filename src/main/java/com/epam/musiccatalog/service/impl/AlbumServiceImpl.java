package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.exception.AlbumNotFoundException;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final MapperFacade mapper;

    @Override
    public List<AlbumDto> getAll() {
        return mapper.mapAsList(albumRepository.findAll(), AlbumDto.class);
    }

    @Override
    public AlbumDto getAlbumById(Long id) {
        Optional<Album> album = albumRepository.findById(id);

        if (album.isEmpty()) {
            log.error("author not found by id {} ...", id);
            throw new AlbumNotFoundException(id);
        }
        return albumToDto(album.get());
    }

    @Override
    public AlbumDto getAlbumByName(String albumName) {
        Optional<Album> album = albumRepository.findAlbumWithPartOfName(albumName);

        if (album.isEmpty()) {
            String message = String.format("Album: %s not found", albumName);
            log.error(message);
            throw new AlbumNotFoundException(message);
        }
        return albumToDto(album.get());
    }

    @Override
    @Transactional
    public AlbumDto save(AlbumDto albumDto) {
        albumDto.setCreatedDate(LocalDate.now());
        Album savedAlbum = albumRepository.save(mapper.map(albumDto, Album.class));
        return mapper.map(savedAlbum, AlbumDto.class);
    }

    @Override
    @Transactional
    public AlbumDto update(Long id, AlbumDto albumDto) {
        Optional<Album> albumById = albumRepository.findById(id);

        if (albumById.isEmpty()) {
            log.error("album not found by id {} ...", id);
            throw new AlbumNotFoundException(id);
        }

        Album updatedAlbum = albumRepository.save(updateAlbum(albumById.get(), albumDto));
        return albumToDto(updatedAlbum);
    }

    @Override
    public void delete(Long id) {
        albumRepository.deleteById(id);
    }

    private AlbumDto albumToDto(Album album) {
        AlbumDto albumDto = mapper.map(album, AlbumDto.class);
        albumDto.setDuration(getSumSongsDurationFromAlbum(album));
        albumDto.setSongNames(getSongNamesOfAlbum(album));
        return albumDto;
    }

    private Album updateAlbum(Album album, AlbumDto albumDto) {
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
