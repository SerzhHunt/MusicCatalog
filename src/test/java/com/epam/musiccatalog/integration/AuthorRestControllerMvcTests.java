package com.epam.musiccatalog.integration;

import com.epam.musiccatalog.controller.AuthorController;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
class AuthorRestControllerMvcTests {
    private static final Long AUTHOR_ID = 1L;

    private static final String BASE_URL = "/authors/";
    private static final String BASE_URL_AND_ID = "/authors/{authorId}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorServiceImpl authorService;

    @Test
    void shouldReturn200CodeAndAllAuthorListWhenSuccessfullyReturnsAllAuthors() throws Exception {
        AuthorDto authorDto = buildAuthorDto(LocalDate.of(1970, 1, 1), Arrays.asList("song1", "song2"));
        authorDto.setId(AUTHOR_ID);

        List<AuthorDto> authorDtoList = Collections.singletonList(authorDto);

        when(authorService.getAll()).thenReturn(authorDtoList);

        this.mvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(AUTHOR_ID))
                .andExpect(jsonPath("$[0].firstname").value(authorDtoList.get(0).getFirstname()))
                .andExpect(jsonPath("$[0].lastname").value(authorDtoList.get(0).getLastname()))
                .andExpect(jsonPath("$[0].birthDate").value(authorDtoList.get(0).getBirthDate().toString()))
                .andExpect(jsonPath("$[0].songNames[0]").value("song1"))
                .andExpect(jsonPath("$[0].songNames[1]").value("song2"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();
    }

    @Test
    void shouldReturn200CodeAndAuthorWhenSuccessfullyReturnAuthorById() throws Exception {
        AuthorDto authorDto = buildAuthorDto(LocalDate.of(1970, 1, 1), Arrays.asList("song1", "song2"));
        authorDto.setId(AUTHOR_ID);

        when(authorService.getAuthorById(anyLong())).thenReturn(authorDto);

        this.mvc.perform(get(BASE_URL_AND_ID, AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.firstname").value(authorDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(authorDto.getLastname()))
                .andExpect(jsonPath("$.birthDate").value(authorDto.getBirthDate().toString()))
                .andExpect(jsonPath("$.songNames[0]").value("song1"))
                .andExpect(jsonPath("$.songNames[1]").value("song2"))
                .andReturn();
    }

    @Test
    void shouldReturn201CodeAndAuthorWhenSuccessfullySaveNewAuthor() throws Exception {
        AuthorDto authorDto = buildAuthorDto(LocalDate.of(1970, 1, 1), null);
        when(authorService.save(any(AuthorDto.class))).thenReturn(authorDto);

        this.mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstname").value(authorDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(authorDto.getLastname()))
                .andExpect(jsonPath("$.birthDate").value(authorDto.getBirthDate().toString()));
    }

    @Test
    void shouldReturn201CodeAndUpdatedAuthorWhenSuccessfullyAuthorHasBeenUpdated() throws Exception {
        AuthorDto updateAuthorDto = buildAuthorDto(null, null);
        updateAuthorDto.setId(AUTHOR_ID);
        when(authorService.update(anyLong(), any(AuthorDto.class))).thenReturn(updateAuthorDto);

        this.mvc.perform(put(BASE_URL_AND_ID, AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAuthorDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updateAuthorDto.getId()))
                .andExpect(jsonPath("$.firstname").value(updateAuthorDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(updateAuthorDto.getLastname()))
                .andReturn();
    }

    @Test
    void shouldReturn204CodeWhenSuccessfullyAuthorHasBeenDeleted() throws Exception {
        mvc.perform(delete(BASE_URL_AND_ID, AUTHOR_ID))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private AuthorDto buildAuthorDto(LocalDate birthDate, List<String> songNames) {
        return AuthorDto.builder()
                .firstname("test")
                .lastname("test")
                .birthDate(birthDate)
                .songNames(songNames)
                .build();
    }
}
