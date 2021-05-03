package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AuthorRepositoryTests {
    private static final Long AUTHOR_ID = 1L;

    @Autowired
    private AuthorRepository repository;

    @Test
    void whenFindByIdThenReturnAuthor() {
        repository.save(buildAuthor(LocalDate.of(1970, 1, 1)));

        Author authorById = repository.findById(AUTHOR_ID).orElse(new Author());

        assertEquals(AUTHOR_ID, authorById.getId());
        assertEquals("test", authorById.getFirstname());
        assertEquals("test", authorById.getLastname());
        assertEquals(LocalDate.of(1970, 1, 1), authorById.getBirthDate());
    }

    @Test
    void whenFindAllThenReturnAuthorList() {
        repository.save(buildAuthor(LocalDate.of(1970, 1, 1)));
        List<Author> allAuthors = repository.findAll();
        assertEquals(1, allAuthors.size());
    }

    private Author buildAuthor(LocalDate birthDate) {
        return Author.builder()
                .firstname("test")
                .lastname("test")
                .birthDate(birthDate)
                .build();
    }
}
