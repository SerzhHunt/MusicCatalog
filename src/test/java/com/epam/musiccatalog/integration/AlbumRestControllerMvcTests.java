package com.epam.musiccatalog.integration;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AlbumController.class)
class AlbumRestControllerMvcTests {
    private static final Long ALBUM_ID = 1L;
    private static final String ALBUM_NAME = "tEs";
    private static final Long EXCEPTION_ID = 999999L;
    private static final String EXCEPTION_ALBUM_NAME = "tEsT";

    private static final String BASE_URL = "/albums/";
    private static final String BASE_URL_AND_ID = "/albums/{albumId}";
    private static final String BASE_URL_AND_SEARCH_BY_NAME = "/albums/name/{albumName}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlbumServiceImpl albumService;

    @Test
    void shouldReturn200CodeAndAllAlbumsListWhenSuccessfullyReturnsAllAlbums() throws Exception {
        AlbumDto albumDto = buildAlbumDto("test", Duration.ofMinutes(55), Arrays.asList("song1", "song2"));
        albumDto.setId(ALBUM_ID);
        List<AlbumDto> albumDtoList = Collections.singletonList(albumDto);

        when(albumService.getAll()).thenReturn(albumDtoList);

        this.mvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(ALBUM_ID))
                .andExpect(jsonPath("$[0].name").value(albumDtoList.get(0).getName()))
                .andExpect(jsonPath("$[0].duration").value(albumDtoList.get(0).getDuration().toString()))
                .andExpect(jsonPath("$[0].songNames[0]").value("song1"))
                .andExpect(jsonPath("$[0].songNames[1]").value("song2"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();
    }

    @Test
    void shouldReturn200CodeAndAlbumWhenSuccessfullyReturnAlbumById() throws Exception {
        AlbumDto albumDto = buildAlbumDto("test", Duration.ofMinutes(55), Arrays.asList("song1", "song2"));
        albumDto.setId(ALBUM_ID);

        when(albumService.getAlbumById(anyLong())).thenReturn(albumDto);

        this.mvc.perform(get(BASE_URL_AND_ID, ALBUM_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(albumDto.getId()))
                .andExpect(jsonPath("$.name").value(albumDto.getName()))
                .andExpect(jsonPath("$.duration").value(albumDto.getDuration().toString()))
                .andExpect(jsonPath("$.songNames[0]").value("song1"))
                .andExpect(jsonPath("$.songNames[1]").value("song2"))
                .andReturn();
    }

    @Test
    void shouldReturn200CodeAndAlbumWhenSuccessfullyReturnAlbumByPartName() throws Exception {
        AlbumDto albumDto = buildAlbumDto("test", Duration.ofMinutes(55), Arrays.asList("song1", "song2"));
        albumDto.setId(ALBUM_ID);

        when(albumService.getAlbumByName(anyString())).thenReturn(albumDto);

        this.mvc.perform(get(BASE_URL_AND_SEARCH_BY_NAME, ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(albumDto.getName()))
                .andExpect(jsonPath("$.duration").value(albumDto.getDuration().toString()))
                .andExpect(jsonPath("$.songNames[0]").value("song1"))
                .andExpect(jsonPath("$.songNames[1]").value("song2"))
                .andReturn();
    }

    @Test
    void shouldReturn201CodeAndAlbumWhenSuccessfullySaveNewAlbum() throws Exception {
        AlbumDto albumDto = buildAlbumDto("test", null, null);

        when(albumService.save(any(AlbumDto.class))).thenReturn(albumDto);

        this.mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(albumDto.getName()))
                .andReturn();
    }

    @Test
    void shouldReturn201CodeAndUpdatedAlbumWhenSuccessfullyAlbumHasBeenUpdated() throws Exception {
        AlbumDto updateAlbumDto = buildAlbumDto("test", null, null);
        updateAlbumDto.setId(ALBUM_ID);

        when(albumService.update(anyLong(), any(AlbumDto.class))).thenReturn(updateAlbumDto);

        getMvcResult(put(BASE_URL_AND_ID, ALBUM_ID), updateAlbumDto);
    }

    private MvcResult getMvcResult(MockHttpServletRequestBuilder method, AlbumDto albumDto) throws Exception {
        return this.mvc.perform(method
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(albumDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(albumDto.getId()))
                .andExpect(jsonPath("$.name").value(albumDto.getName()))
                .andReturn();
    }

    @Test
    void shouldReturn204CodeWhenSuccessfullyAlbumHasBeenDeleted() throws Exception {
        mvc.perform(delete(BASE_URL_AND_ID, ALBUM_ID))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void whenAlbumByIdNotFoundThenReturn404Code() throws Exception {
        when(albumService.getAlbumById(anyLong())).thenThrow(new AlbumNotFoundException(EXCEPTION_ID));

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

        when(albumService.getAlbumByName(anyString())).thenThrow(new AlbumNotFoundException(EXCEPTION_ALBUM_NAME));

        mvc.perform(get(BASE_URL_AND_SEARCH_BY_NAME, EXCEPTION_ALBUM_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlbumNotFoundException))
                .andExpect(result -> assertEquals(String.format("Album with name is \"%s\" not found!", EXCEPTION_ALBUM_NAME),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenSaveAlbumWithoutNameThenReturn400Code() throws Exception {
        AlbumDto invalidAuthorDto = buildAlbumDto(null, null, null);

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveAlbumWithDurationThenReturn400Code() throws Exception {
        AlbumDto invalidAuthorDto = buildAlbumDto("test", Duration.ofMinutes(55), null);

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveAlbumWithSongNamesThenReturn400Code() throws Exception {
        AlbumDto invalidAuthorDto = buildAlbumDto("test", null, Arrays.asList("song1", "song2"));

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
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
