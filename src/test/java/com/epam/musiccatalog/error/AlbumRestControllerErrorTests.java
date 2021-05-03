package com.epam.musiccatalog.error;

import com.epam.musiccatalog.controller.AlbumController;
import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.exception.AlbumNotFoundException;
import com.epam.musiccatalog.service.impl.AlbumServiceImpl;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AlbumController.class)
class AlbumRestControllerErrorTests {
    private static final String BASE_URL = "/albums/";
    private static final String BASE_URL_AND_ID = "/albums/{albumId}";
    private static final String BASE_URL_AND_SEARCH_BY_NAME = "/albums/name/{albumName}";
    private static final Long ALBUM_ID = 1L;
    private static final Long EXCEPTION_ID = 999999L;
    private static final String INCORRECT_ALBUM_NAME = "tEsT";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlbumServiceImpl albumService;

    @Test
    void whenAlbumByIdNotFoundThenReturn404Code() throws Exception {
        when(albumService.getAlbumById(EXCEPTION_ID)).thenThrow(new AlbumNotFoundException(EXCEPTION_ID));

        mvc.perform(get(BASE_URL_AND_ID, EXCEPTION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlbumNotFoundException))
                .andExpect(result -> assertEquals(String.format("Album id not found : %s", EXCEPTION_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenAlbumByNameNotFoundThenReturn404Code() throws Exception {
        String errorMsg = String.format("Album: %s not found", INCORRECT_ALBUM_NAME);

        when(albumService.getAlbumByName(INCORRECT_ALBUM_NAME)).thenThrow(new AlbumNotFoundException(errorMsg));

        mvc.perform(get(BASE_URL_AND_SEARCH_BY_NAME, INCORRECT_ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlbumNotFoundException))
                .andExpect(result -> assertEquals(errorMsg,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenUpdateAlbumByIdNotFoundThenReturn404Code() throws Exception {
        when(albumService.update(anyLong(), any(AlbumDto.class))).thenThrow(new AlbumNotFoundException(ALBUM_ID));

        AlbumDto albumDto = buildAlbumDto("test", null, null);
        albumDto.setId(ALBUM_ID);

        mvc.perform(put(BASE_URL_AND_ID, ALBUM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumDto)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlbumNotFoundException))
                .andExpect(result -> assertEquals(String.format("Album id not found : %s", ALBUM_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenSaveAlbumWithNameNotValidThenReturn400Code() throws Exception {
        AlbumDto invalidAuthorDto = buildAlbumDto(null, null, null);

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveAlbumWithDurationNotValidThenReturn400Code() throws Exception {
        AlbumDto invalidAuthorDto = buildAlbumDto("test", Duration.ofMinutes(55), null);

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveAlbumWithSongNamesNotValidThenReturn400Code() throws Exception {
        AlbumDto invalidAuthorDto = buildAlbumDto("test", null, Arrays.asList("song1", "song2"));

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateAlbumWithoutIdThenReturn400Code() throws Exception {
        AlbumDto albumDto = buildAlbumDto("test", null, null);

        mvc.perform(put(BASE_URL_AND_ID, ALBUM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateAuthorWithDurationThenReturn400Code() throws Exception {
        AlbumDto albumDto = buildAlbumDto("test", Duration.ofMinutes(55), null);
        albumDto.setId(ALBUM_ID);

        mvc.perform(put(BASE_URL_AND_ID, ALBUM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateAuthorWithSongNamesThenReturn400Code() throws Exception {
        AlbumDto albumDto = buildAlbumDto("test", null, Arrays.asList("song1", "song2"));
        albumDto.setId(ALBUM_ID);

        mvc.perform(put(BASE_URL_AND_ID, ALBUM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private AlbumDto buildAlbumDto(String name, Duration duration, List<String> songNames) {
        return AlbumDto.builder()
                .name(name)
                .duration(duration)
                .songNames(songNames)
                .build();
    }
}
