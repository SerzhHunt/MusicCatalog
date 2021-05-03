package com.epam.musiccatalog.integration;

import com.epam.musiccatalog.controller.AlbumController;
import com.epam.musiccatalog.dto.AlbumDto;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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
    private static final String BASE_URL = "/albums/";
    private static final String BASE_URL_AND_SEARCH_BY_NAME = "/albums/name/{albumName}";
    private static final String BASE_URL_AND_ID = "/albums/{albumId}";
    private static final Long ALBUM_ID = 1L;
    private static final String ALBUM_NAME = "tEs";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlbumServiceImpl albumService;

    @Test
    void shouldReturn200CodeAndAllAlbumsListWhenSuccessfullyReturnsAllAlbums() throws Exception {
        AlbumDto albumDto = buildAlbumDto(Duration.ofMinutes(55), Arrays.asList("song1", "song2"));
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
        AlbumDto albumDto = buildAlbumDto(Duration.ofMinutes(55), Arrays.asList("song1", "song2"));
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
        AlbumDto albumDto = buildAlbumDto(Duration.ofMinutes(55), Arrays.asList("song1", "song2"));
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
        AlbumDto albumDto = buildAlbumDto(null, null);

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
        AlbumDto updateAlbumDto = buildAlbumDto(null, null);
        updateAlbumDto.setId(ALBUM_ID);
        when(albumService.update(anyLong(), any(AlbumDto.class))).thenReturn(updateAlbumDto);

        this.mvc.perform(put(BASE_URL_AND_ID, ALBUM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAlbumDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updateAlbumDto.getId()))
                .andExpect(jsonPath("$.name").value(updateAlbumDto.getName()))
                .andReturn();
    }

    @Test
    void shouldReturn204CodeWhenSuccessfullyAlbumHasBeenDeleted() throws Exception {
        mvc.perform(delete(BASE_URL_AND_ID, ALBUM_ID))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private AlbumDto buildAlbumDto(Duration duration, List<String> songNames) {
        return AlbumDto.builder()
                .name("test")
                .duration(duration)
                .songNames(songNames)
                .build();
    }
}
