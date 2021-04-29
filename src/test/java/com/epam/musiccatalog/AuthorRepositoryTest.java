package com.epam.musiccatalog;

import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    public void testAuthorById() {
        Author author = new Author("name","name", LocalDate.of(1970,1,1), Collections.emptyList());
        Author savedAuthor = repository.save(author);
        assertEquals(author.getFirstname(),savedAuthor.getFirstname());
    }

}
