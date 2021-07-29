package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.repository.AuthorRepository;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    private static final Long AUTHOR_ID = 1L;

    @Mock
    private AuthorRepository repository;

    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private AuthorServiceImpl service;

    private Author author;
    private AuthorDto authorDto;


    @BeforeEach
    void setUp() {
        author = buildAuthor();
        author.setId(AUTHOR_ID);

        authorDto = buildAuthorDto();
        authorDto.setId(AUTHOR_ID);
    }

    @Test
    void getAll() {
        List<Author> authorList = Collections.singletonList(author);

        when(repository.findAll()).thenReturn(authorList);
        when(mapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        List<AuthorDto> authorDtoList = service.getAll();

        check(authorList.get(0), authorDtoList.get(0));
    }

    @Test
    void getAuthorById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(author));
        when(mapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto authorById = service.getAuthorById(AUTHOR_ID);

        check(author, authorById);
    }

    @Test
    void save() {
        when(repository.save(any(Author.class))).thenReturn(author);
        when(mapper.map(authorDto, Author.class)).thenReturn(author);
        when(mapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto savedAuthorDto = service.save(authorDto);

        check(author, savedAuthorDto);
    }

    @Test
    void update() {
        when(repository.findById(AUTHOR_ID)).thenReturn(Optional.of(author));
        when(repository.save(any(Author.class))).thenReturn(author);
        when(mapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto updateAuthorDto = service.update(AUTHOR_ID, authorDto);

        check(author, updateAuthorDto);
    }

    private void check(Author author, AuthorDto authorDto) {
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getFirstname(), authorDto.getFirstname());
        assertEquals(author.getLastname(), authorDto.getLastname());
        assertEquals(author.getBirthDate(), authorDto.getBirthDate());
    }

    private Author buildAuthor() {
        return Author.builder()
                .firstname("firstName")
                .lastname("lastName")
                .songs(Collections.singletonList(new Song()))
                .birthDate(LocalDate.of(1970, 1, 1))
                .build();
    }

    private AuthorDto buildAuthorDto() {
        return AuthorDto.builder()
                .firstname("firstName")
                .lastname("lastName")
                .birthDate(LocalDate.of(1970, 1, 1))
                .songNames(Arrays.asList("S1", "S2"))
                .build();
    }
}