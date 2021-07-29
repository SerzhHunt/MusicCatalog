package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.repository.AlbumRepository;
import com.epam.musiccatalog.repository.SongRepository;
import com.epam.musiccatalog.service.impl.SongServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {
    private static final Long SONG_ID = 1L;
    private static final Long ALBUM_ID = 1L;
    private static final String PART_ALBUM_NAME = "test";

    @Mock
    private SongRepository songRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private SongServiceImpl service;

    private Album album;
    private SongDto songDto;
    private Song song;

    @BeforeEach
    void setUp() {
        song = buildSong();
        song.setId(SONG_ID);

        songDto = buildSongDto();
        songDto.setId(SONG_ID);

        album = buildAlbum();
        album.setId(ALBUM_ID);
    }

    @Test
    void getAll() {
        List<Song> songList = Collections.singletonList(song);

        when(songRepository.findAll()).thenReturn(songList);
        when(mapper.map(song, SongDto.class)).thenReturn(songDto);

        List<SongDto> songDtoList = service.getAll();

        check(songList.get(0), songDtoList.get(0));
    }

    @Test
    void getSongById() {
        when(albumRepository.findAlbumWithPartOfName(anyString())).thenReturn(Optional.of(album));
        when(songRepository.findSongByIdInAlbumById(anyLong(), anyLong())).thenReturn(Optional.of(song));
        when(mapper.map(song, SongDto.class)).thenReturn(songDto);

        SongDto songDtoById = service.getSongById(PART_ALBUM_NAME, SONG_ID);

        check(song, songDtoById);
    }

    @Test
    void save() {
        when(albumRepository.findAlbumWithPartOfName(anyString())).thenReturn(Optional.of(album));
        when(mapper.map(songDto, Song.class)).thenReturn(song);
        when(songRepository.save(any(Song.class))).thenReturn(song);
        when(mapper.map(song, SongDto.class)).thenReturn(songDto);

        SongDto savedSongDto = service.save(PART_ALBUM_NAME, songDto);

        check(song, savedSongDto);
    }

    @Test
    void update() {
        when(songRepository.findById(anyLong())).thenReturn(Optional.of(song));
        when(songRepository.save(any(Song.class))).thenReturn(song);
        when(mapper.map(song, SongDto.class)).thenReturn(songDto);

        SongDto updatedSongDto = service.update(SONG_ID, songDto);

        check(song, updatedSongDto);
    }


    private void check(Song song, SongDto songDto) {
        assertEquals(song.getId(), songDto.getId());
        assertEquals(song.getName(), songDto.getName());
        assertEquals(song.getAlbum().getName(), songDto.getAlbumName());
        assertEquals(song.getDuration(), songDto.getDuration());
        assertEquals("Test Author", songDto.getAuthorNames().get(0));
    }

    private Song buildSong() {
        return Song.builder()
                .name("Test Song")
                .duration(Duration.ofMinutes(5))
                .album(new Album())
                .authors(Collections.singletonList(new Author(
                        "Test", "Author", LocalDate.of(1970, 1, 1), Collections.emptyList())))
                .build();
    }

    private SongDto buildSongDto() {
        return SongDto.builder()
                .name("Test Song")
                .duration(Duration.ofMinutes(5))
                .albumName("Test Album")
                .authorNames(Collections.singletonList("Test Author"))
                .build();
    }

    private Album buildAlbum() {
        return Album.builder()
                .name("Test Album")
                .songs(Collections.singletonList(new Song()))
                .createdDate(LocalDate.now())
                .build();
    }
}