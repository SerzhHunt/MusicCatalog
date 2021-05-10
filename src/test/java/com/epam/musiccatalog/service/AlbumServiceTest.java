package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.mapper.AlbumMapper;
import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.service.impl.AlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {
    private final Long ALBUM_ID = 1L;

    @Mock
    private AlbumRepository repository;

    @Mock
    private AlbumMapper mapper;

    @InjectMocks
    private AlbumServiceImpl service;

    private Album album;
    private AlbumDto albumDto;

    @BeforeEach
    void setUp() {
        album = buildAlbum();
        album.setId(ALBUM_ID);

        albumDto = buildAlbumDto();
        albumDto.setId(ALBUM_ID);
    }


    @Test
    void getAll() {
        List<Album> albumList = Collections.singletonList(album);

        when(repository.findAll()).thenReturn(albumList);
        when(mapper.toAlbumDto(any(Album.class))).thenReturn(albumDto);

        List<AlbumDto> albumDtoList = service.getAll();

        check(albumList.get(0), albumDtoList.get(0));
    }

    @Test
    void getAlbumById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(album));
        when(mapper.toAlbumDto(any(Album.class))).thenReturn(albumDto);

        AlbumDto albumById = service.getAlbumById(ALBUM_ID);

        check(album, albumById);
    }

    @Test
    void getAlbumByName() {
        when(repository.findAlbumWithPartOfName(anyString())).thenReturn(Optional.of(album));
        when(mapper.toAlbumDto(any(Album.class))).thenReturn(albumDto);

        AlbumDto albumByName = service.getAlbumByName("tes");

        check(album, albumByName);
    }

    @Test
    void save() {
        when(repository.save(any(Album.class))).thenReturn(album);
        when(mapper.toAlbum(albumDto)).thenReturn(album);
        when(mapper.toAlbumDto(any(Album.class))).thenReturn(albumDto);

        AlbumDto savedAlbum = service.save(albumDto);

        check(album, savedAlbum);
    }

    @Test
    void update() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(album));
        when(repository.save(any(Album.class))).thenReturn(album);
        when(mapper.toAlbumDto(any(Album.class))).thenReturn(albumDto);

        AlbumDto updateAlbumDto = service.update(ALBUM_ID, albumDto);

        check(album, updateAlbumDto);
    }

    private void check(Album album, AlbumDto albumDto) {
        assertEquals(album.getId(), albumDto.getId());
        assertEquals(album.getName(), albumDto.getName());
        assertEquals(album.getCreatedDate(), albumDto.getCreatedDate());
    }

    private Album buildAlbum() {
        return Album.builder()
                .name("Test Album")
                .createdDate(LocalDate.now())
                .build();
    }

    private AlbumDto buildAlbumDto() {
        return AlbumDto.builder()
                .name("Test Album")
                .duration(Duration.ofMinutes(30))
                .createdDate(LocalDate.now())
                .songNames(Arrays.asList("S1", "S2"))
                .build();
    }
}