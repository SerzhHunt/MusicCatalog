package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.service.AlbumService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final MapperFacade mapper;

//    @Override
//    @Transactional
//    public List<AlbumDto> getAll() {
//        List<Album> albums = albumRepository.findAll();
//        return albumMapper.mapAsList(albums, AlbumDto.class);
//    }

    @Override
    public List<AlbumDto> getAll() {
        return null;
    }

    @Override
    @Transactional
    public AlbumDto getAlbumById(Long id) {
        Optional<Album> album = albumRepository.findById(id);
        Album testAlbum = new Album();

        if (album.isPresent()) {
            long sum = album.get().getSongs().stream().mapToLong(s->s.getDuration().toMinutes()).sum();
            AlbumDto albumDto = mapper.map(album.get(), AlbumDto.class);
            albumDto.setDuration(Duration.ofMinutes(sum));
            return albumDto;
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
//
//    @Override
//    @Transactional
//    public AlbumDto save(AlbumDto albumDto) {
//        if (albumDto != null) {
//            Album album = albumMapper.map(albumDto, Album.class);
//            Album savedAlbum = albumRepository.save(album);
//            return albumMapper.map(savedAlbum, AlbumDto.class);
//        }
//        return null; //TODO check null throw exception
//    }
//
//    @Override
//    @Transactional
//    public AlbumDto update(Long id, AlbumDto albumDto) {
//        AlbumDto updateAlbumDto = getAlbumById(id);
//
//        updateAlbumDto.setId(albumDto.getId());
//        updateAlbumDto.setName(albumDto.getName());
//        updateAlbumDto.setDuration(albumDto.getDuration());
//        updateAlbumDto.setAuthors(albumDto.getAuthors());
//        updateAlbumDto.setSongs(albumDto.getSongs());
//        updateAlbumDto.setCreatedDate(albumDto.getCreatedDate()); //TODO try/catch?
//
//        return save(updateAlbumDto);
//    }
//
//    @Override
//    public void delete(Long id) {
//        albumRepository.deleteById(id);
//    }
}
