package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.exception.AlbumNotFoundException;
import com.epam.musiccatalog.exception.SongNotFoundException;
import com.epam.musiccatalog.mapper.SongMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final SongMapper songMapper;

    @Override
    public List<SongDto> getAll() {
        return songRepository.findAll().stream()
                .map(songMapper::toSongDto)
                .collect(Collectors.toList());
    }

    @Override
    public SongDto getSongById(String albumName, Long songId) {
        Optional<Album> album = albumRepository.findAlbumWithPartOfName(albumName);

        if (album.isEmpty()) {
            String errorMsg = String.format("Album with name is \"%s\" not found!", albumName);
            log.error(errorMsg);
            throw new AlbumNotFoundException(albumName);
        }

        Optional<Song> song = songRepository.findSongByIdInAlbumById(album.get().getId(), songId);

        if (song.isEmpty()) {
            log.error("Song not found by id {} ...", songId);
            throw new SongNotFoundException(songId);
        }

        return songToDto(song.get());
    }

    @Override
    @Transactional
    public SongDto save(String albumName, SongDto songDto) {
        Optional<Album> album = albumRepository.findAlbumWithPartOfName(albumName);

        if (album.isEmpty()) {
            String errorMsg = String.format("Album with name is \"%s\" not found!", albumName);
            log.error(errorMsg);
            throw new AlbumNotFoundException(albumName);
        }

        Song song = songMapper.toSong(songDto);
        song.setAlbum(album.get());

        Song savedSong = songRepository.save(song);
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
        SongDto songDto = songMapper.toSongDto(song);
        songDto.setAuthorNames(getAuthorNamesOfSong(song));
        songDto.setAlbumName(getAlbumNameOfSong(song));
        return songDto;
    }

    private Song updateSong(Song song, SongDto songDto) {
        song.setName(songDto.getName());
        song.setDuration(songDto.getDuration());
        return song;
    }

    private List<String> getAuthorNamesOfSong(Song song) {
        return song.getAuthors()
                .stream()
                .map(author -> author.getFirstname().concat(" ").concat(author.getLastname()))
                .collect(Collectors.toList());
    }

    private String getAlbumNameOfSong(Song song) {
        return song.getAlbum().getName();
    }
}
