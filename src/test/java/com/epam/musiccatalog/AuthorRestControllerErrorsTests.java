package com.epam.musiccatalog;

import com.epam.musiccatalog.controller.AuthorController;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.exception.AuthorNotFoundException;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
class AuthorRestControllerErrorsTests {
    private static final String BASE_URL = "/authors/";
    private static final String BASE_URL_AND_ID = "/authors/{authorId}";
    private static final Long EXCEPTION_ID = 999999L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AuthorServiceImpl authorService;

    private AuthorDto invalidAuthorDto;

    @BeforeEach
    public void setUp() {
        invalidAuthorDto = new AuthorDto();
        invalidAuthorDto.setFirstname(null);
        invalidAuthorDto.setLastname("MockLastname");
        invalidAuthorDto.setBirthDate(LocalDate.of(1970, 1, 1));
    }

    @Test
    void shouldReturn404codeWhenAuthorByIdIsNotFound() throws Exception {
        when(authorService.getAuthorById(EXCEPTION_ID)).thenThrow(new AuthorNotFoundException(EXCEPTION_ID));

        mvc.perform(get(BASE_URL_AND_ID, EXCEPTION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthorNotFoundException))
                .andExpect(result -> assertEquals(String.format("Author id not found : %s", EXCEPTION_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void shouldReturn400CodeWhenAuthorForSaveIsNotValid() throws Exception {
        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAuthorDto))) //201? oO
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
