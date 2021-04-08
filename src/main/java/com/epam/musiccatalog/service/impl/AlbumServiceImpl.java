package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.service.AlbumService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final MapperFacade mapper;

    @Override
    public List<AlbumDto> getAll() {
        return albumRepository.findAll()
                .stream()
                .map(a -> mapper.map(a, AlbumDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AlbumDto getAlbumById(Long id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            return mapper.map(album, AlbumDto.class);
//            AlbumDto albumDto = mapper.map(album, AlbumDto.class);
//
//            albumDto.setDuration(Duration.ofMinutes(album.get().getSongs()
//                    .stream()
//                    .map(Song::getDuration)
//                    .count()));
//
//            albumDto.setSongs(album.get().getSongs()
//                    .stream()
//                    .map(Song::getName)
//                    .collect(Collectors.toList()));
//
//            albumDto.setAuthors(album.get().getSongs()
//                    .stream()
//                    .map(s -> s.getAuthor().getFirstname().concat(" " + s.getAuthor().getLastname()))
//                    .collect(Collectors.toList()));
//
//            return albumDto;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public AlbumDto save(AlbumDto albumDto) {
        return null;
    }

    @Override
    public AlbumDto update(Long id, AlbumDto albumDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
