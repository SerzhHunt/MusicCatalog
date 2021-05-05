package com.epam.musiccatalog.error;

import com.epam.musiccatalog.controller.AuthorController;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.exception.AuthorNotFoundException;
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
@WebMvcTest(AuthorController.class)
class AuthorRestControllerErrorsTests {
    private static final Long AUTHOR_ID = 1L;
    private static final Long EXCEPTION_ID = 999999L;

    private static final String BASE_URL = "/authors";
    private static final String BASE_URL_AND_ID = "/authors/{authorId}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorServiceImpl authorService;

    @Test
    void whenAuthorByIdNotFoundThenReturn404Code() throws Exception {
        when(authorService.getAuthorById(anyLong())).thenThrow(new AuthorNotFoundException(EXCEPTION_ID));

        mvc.perform(get(BASE_URL_AND_ID, EXCEPTION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthorNotFoundException))
                .andExpect(result -> assertEquals(String.format("Author id not found : %s", EXCEPTION_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenSaveAuthorWithoutFirstNameNotValidThenReturn400Code() throws Exception {
        AuthorDto invalidAuthorDto = buildAuthorDto(null, "test", LocalDate.of(1970, 1, 1));

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveAuthorWithoutLastNameThenReturn400Code() throws Exception {
        AuthorDto invalidAuthorDto = buildAuthorDto("test", null, LocalDate.of(1970, 1, 1));

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenSaveAuthorWithIdThenReturn400Code() throws Exception {
        AuthorDto invalidAuthorDto = buildAuthorDto("test", "test", LocalDate.of(1970, 1, 1));
        invalidAuthorDto.setId(1L);

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateAuthorByIdNotFoundThenReturn404Code() throws Exception {
        when(authorService.update(anyLong(), any(AuthorDto.class))).thenThrow(new AuthorNotFoundException(AUTHOR_ID));

        AuthorDto authorDto = buildAuthorDto("test", "test", null);
        authorDto.setId(AUTHOR_ID);

        mvc.perform(put(BASE_URL_AND_ID, AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthorNotFoundException))
                .andExpect(result -> assertEquals(String.format("Author id not found : %s", AUTHOR_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void whenUpdateAuthorWithoutIdThenReturn400Code() throws Exception {
        AuthorDto authorDto = buildAuthorDto("test", "test", LocalDate.of(1970, 1, 1));

        mvc.perform(put(BASE_URL_AND_ID, AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenUpdateAuthorWithDateOfBirthThenReturn400Code() throws Exception {
        AuthorDto authorDto = buildAuthorDto("test", "test", LocalDate.of(1970, 1, 1));
        authorDto.setId(AUTHOR_ID);

        mvc.perform(put(BASE_URL_AND_ID, AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private AuthorDto buildAuthorDto(String firstName, String lastName, LocalDate birthDate) {
        return AuthorDto.builder()
                .firstname(firstName)
                .lastname(lastName)
                .birthDate(birthDate)
                .build();
    }
}
