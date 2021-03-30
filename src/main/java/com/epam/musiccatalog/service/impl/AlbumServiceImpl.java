package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.mapper.AlbumMapper;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    @Override
    @Transactional
    public List<AlbumDto> getAll() {
        List<Album> albums = albumRepository.findAll();
        return albumMapper.mapAsList(albums, AlbumDto.class);
    }

    @Override
    @Transactional
    public AlbumDto getAlbumById(Long id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            throw new RuntimeException();//TODO change exception
        }
        return albumMapper.map(album.get(), AlbumDto.class);
    }

    @Override
    @Transactional
    public AlbumDto save(AlbumDto albumDto) {
        if (albumDto != null) {
            Album album = albumMapper.map(albumDto, Album.class);
            Album savedAlbum = albumRepository.save(album);
            return albumMapper.map(savedAlbum, AlbumDto.class);
        }
        return null; //TODO check null throw exception
    }

    @Override
    @Transactional
    public AlbumDto update(Long id, AlbumDto albumDto) {
        AlbumDto updateAlbumDto = getAlbumById(id);

        updateAlbumDto.setId(albumDto.getId());
        updateAlbumDto.setName(albumDto.getName());
        updateAlbumDto.setDuration(albumDto.getDuration());
        updateAlbumDto.setAuthors(albumDto.getAuthors());
        updateAlbumDto.setSongs(albumDto.getSongs());
        updateAlbumDto.setCreatedDate(albumDto.getCreatedDate()); //TODO try/catch?

        return save(updateAlbumDto);
    }

    @Override
    public void delete(Long id) {
        albumRepository.deleteById(id);
    }
}
