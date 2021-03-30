package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.mapper.SongMapper;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.repository.SongRepository;
import com.epam.musiccatalog.service.AlbumService;
import com.epam.musiccatalog.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final SongMapper songMapper;
    private final AlbumService albumService;

    @Autowired
    public SongServiceImpl(AlbumRepository albumRepository, SongRepository songRepository,
                           SongMapper songMapper, AlbumService albumService) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.albumService = albumService;
    }

    @Override
    public List<SongDto> getAll() {
        return albumRepository.findAll()
                .stream()
                .map(Album::getSongs)
                .map(song -> songMapper.map(song, SongDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SongDto getSongById(Long albumId, Long songId) {
        Optional<Album> album = albumRepository.findById(albumId);
        Optional<Song> song = songRepository.findById(songId);

        if (album.isEmpty() || song.isEmpty()) {
            throw new RuntimeException();//TODO change exception
        }
        AlbumDto albumDto = songMapper.map(album.get(), AlbumDto.class);
        SongDto songDto = songMapper.map(song.get(), SongDto.class);

        Optional<SongDto> findSong = albumDto.getSongs().stream()
                .filter(s -> s.getId().equals(songDto.getId()))
                .findFirst();

        if (findSong.isEmpty()) {
            throw new RuntimeException();
        } else {
            return findSong.get();
        }
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
