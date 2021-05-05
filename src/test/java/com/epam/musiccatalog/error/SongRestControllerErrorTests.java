package com.epam.musiccatalog.error;

import com.epam.musiccatalog.controller.SongController;
import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.exception.AlbumNotFoundException;
import com.epam.musiccatalog.exception.SongNotFoundException;
import com.epam.musiccatalog.service.impl.SongServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SongController.class)
class SongRestControllerErrorTests {
    private static final Long SONG_ID = 1L;
    private static final Long EXCEPTION_ID = 99999L;
    private static final String ALBUM_NAME = "TestAlbum";
    private static final String EXCEPTION_ALBUM_NAME = "error";

    private final static String BASE_URL_AND_ID = "/songs/{song_id}";
    private final static String BASE_URL_AND_ALBUM_NAME = "/songs/{albumName}/";
    private final static String BASE_URL_AND_ALBUM_NAME_AND_SONG_ID = "/songs/{albumName}/{songId}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SongServiceImpl songService;

    @Test
    void whenSongByNameNotFoundThenReturn404Code() throws Exception {
        when(songService.getSongById(anyString(), anyLong())).thenThrow(new AlbumNotFoundException(EXCEPTION_ALBUM_NAME));

        mvc.perform(get(BASE_URL_AND_ALBUM_NAME_AND_SONG_ID, EXCEPTION_ALBUM_NAME, SONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlbumNotFoundException))
                .andExpect(result -> assertEquals(String.format("Album with name is \"%s\" not found!", EXCEPTION_ALBUM_NAME),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenSongByIdNotFoundThenReturn404Code() throws Exception {
        when(songService.getSongById(anyString(), anyLong())).thenThrow(new SongNotFoundException(EXCEPTION_ID));

        mvc.perform(get(BASE_URL_AND_ALBUM_NAME_AND_SONG_ID, ALBUM_NAME, EXCEPTION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SongNotFoundException))
                .andExpect(result -> assertEquals(String.format("Song id not found : %s", EXCEPTION_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenSaveSongWithNonExistAlbumNameThenReturn404Code() throws Exception {
        when(songService.save(anyString(), any(SongDto.class))).thenThrow(new AlbumNotFoundException(EXCEPTION_ALBUM_NAME));

        SongDto songDto = buildSong("test", Duration.ofMinutes(3), Arrays.asList("Author1", "Author2"), null);

        mvc.perform(post(BASE_URL_AND_ALBUM_NAME, EXCEPTION_ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(songDto)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlbumNotFoundException))
                .andExpect(result -> assertEquals(String.format("Album with name is \"%s\" not found!", EXCEPTION_ALBUM_NAME),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenSaveSongWithoutFirstNameThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong(null, Duration.ofMinutes(3), Arrays.asList("Author1", "Author2"), null);

        mvc.perform(post(BASE_URL_AND_ALBUM_NAME, ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveSongWithoutDurationThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong("test", null, Arrays.asList("Author1", "Author2"), null);

        mvc.perform(post(BASE_URL_AND_ALBUM_NAME, ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveSongWithoutAuthorsThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong("test", Duration.ofMinutes(4), null, null);

        mvc.perform(post(BASE_URL_AND_ALBUM_NAME, ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveSongWithAlbumNameThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong("test", Duration.ofMinutes(4), Arrays.asList("Author1", "Author2"), "test");

        mvc.perform(post(BASE_URL_AND_ALBUM_NAME, ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateSongByIdNotFoundThenReturn404Code() throws Exception {
        when(songService.update(anyLong(), any(SongDto.class))).thenThrow(new SongNotFoundException(EXCEPTION_ID));

        SongDto songDto = buildSong("test", null, Arrays.asList("Author1", "Author2"), null);
        songDto.setId(EXCEPTION_ID);

        mvc.perform(put(BASE_URL_AND_ID, EXCEPTION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(songDto)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SongNotFoundException))
                .andExpect(result -> assertEquals(String.format("Song id not found : %s", EXCEPTION_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenUpdateSongWithoutIdThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong("test", null, Arrays.asList("Author1", "Author2"), null);

        mvc.perform(put(BASE_URL_AND_ID, SONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateSongWithoutNameThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong(null, null, Arrays.asList("Author1", "Author2"), null);
        invalidSongDto.setId(SONG_ID);

        mvc.perform(put(BASE_URL_AND_ID, SONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateSongWithoutAuthorsThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong("test", null, null, null);
        invalidSongDto.setId(SONG_ID);

        mvc.perform(put(BASE_URL_AND_ID, SONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateSongWithDurationThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong("test", Duration.ofMinutes(4), Arrays.asList("Author1", "Author2"), null);
        invalidSongDto.setId(SONG_ID);

        mvc.perform(put(BASE_URL_AND_ID, SONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateSongWithAlbumNameThenReturn400Code() throws Exception {
        SongDto invalidSongDto = buildSong("test", null, Arrays.asList("Author1", "Author2"), "test");
        invalidSongDto.setId(SONG_ID);

        mvc.perform(put(BASE_URL_AND_ID, SONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSongDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private SongDto buildSong(String name, Duration duration, List<String> authorNames, String albumName) {
        return SongDto.builder()
                .name(name)
                .duration(duration)
                .authorNames(authorNames)
                .albumName(albumName)
                .build();
    }
}
