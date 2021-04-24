package com.epam.musiccatalog;

import com.epam.musiccatalog.controller.AuthorController;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
class AuthorRestControllerMvcTest {
    private static final String BASE_URL = "/authors/";
    private static final String BASE_URL_AND_ID = "/authors/{authorId}";
    private static final Long AUTHOR_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorServiceImpl authorService;

    private AuthorDto authorDto;
    private AuthorDto updatedAuthorDto;
    private List<AuthorDto> authorDtoList;

    @BeforeEach
    public void setUp() {
        authorDto = new AuthorDto();
        authorDto.setFirstname("MockFirstname");
        authorDto.setLastname("MockLastname");
        authorDto.setBirthDate(LocalDate.of(1970, 1, 1));
        authorDto.setSongNames(Collections.emptyList());

        updatedAuthorDto = new AuthorDto();
        updatedAuthorDto.setFirstname("UpdatedMockFirstname");
        updatedAuthorDto.setLastname("UpdatedMockLastname");
        updatedAuthorDto.setBirthDate(LocalDate.of(1970, 1, 1));
        updatedAuthorDto.setSongNames(Arrays.asList("MockSong1", "MockSong2"));

        authorDtoList = Arrays.asList(authorDto, updatedAuthorDto);

        when(authorService.getAll()).thenReturn(authorDtoList);
        when(authorService.getAuthorById(AUTHOR_ID)).thenReturn(authorDto);
        when(authorService.save(any(AuthorDto.class))).thenReturn(authorDto);
        when(authorService.update(anyLong(), any(AuthorDto.class))).thenReturn(updatedAuthorDto);
    }

    @Test
    void shouldReturn200CodeAndAllAuthorsListWhenSuccessfullyReturnsAllAuthors() throws Exception {
        this.mvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(this.authorDtoList.size()))
                .andReturn();
    }

    @Test
    void shouldReturn200CodeAndAuthorWhenSuccessfullyReturnAuthorById() throws Exception {
        this.mvc.perform(get(BASE_URL_AND_ID, AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstname").value(this.authorDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(this.authorDto.getLastname()))
                .andExpect(jsonPath("$.birthDate").value(this.authorDto.getBirthDate().toString()))
                .andReturn();
    }

    @Test
    void shouldReturn201CodeAndAuthorWhenSuccessfullySaveNewAuthor() throws Exception {
        this.mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstname").value(this.authorDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(this.authorDto.getLastname()))
                .andExpect(jsonPath("$.birthDate").value(this.authorDto.getBirthDate().toString()));
    }

    @Test
    void shouldReturn201CodeAndUpdatedAuthorWhenSuccessfullyAuthorHasBeenUpdated() throws Exception {
        this.mvc.perform(put(BASE_URL_AND_ID, AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAuthorDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstname").value(this.updatedAuthorDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(this.updatedAuthorDto.getLastname()))
                .andExpect(jsonPath("$.songNames.size()").value(updatedAuthorDto.getSongNames().size()))
                .andReturn();
    }

    @Test
    void shouldReturn204CodeWhenSuccessfullyAuthorHasBeenDeleted() throws Exception {
        mvc.perform(delete(BASE_URL_AND_ID, AUTHOR_ID))
                .andExpect(status().isNoContent());
    }
}