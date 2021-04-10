package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.exception.author.AuthorConvertingException;
import com.epam.musiccatalog.exception.author.AuthorException;
import com.epam.musiccatalog.exception.author.AuthorNotFoundException;
import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.repository.AuthorRepository;
import com.epam.musiccatalog.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("authorService")
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final MapperFacade mapper;

    @Override
    @Transactional
    public List<AuthorDto> getAll() throws AuthorException {
        try {
            List<AuthorDto> authors = new ArrayList<>();
            for (Author author : authorRepository.findAll()) {
                AuthorDto authorDto = authorToDto(author);
                authors.add(authorDto);
            }
            return authors;
        } catch (Exception e) {
            String message = "exception while getting all authors";
            log.error(message);
            throw new AuthorException(message, e);
        }
    }

    @Override
    @Transactional
    public AuthorDto getAuthorById(Long id) throws AuthorException {
        try {
            Optional<Author> author = authorRepository.findById(id);

            if (author.isEmpty()) {
                log.error("author not found by id {} ...", id);
                throw new AuthorNotFoundException(id);
            }
            return authorToDto(author.get());
        } catch (Exception e) {
            String message = "exception while getting author from db";
            log.error(message, e.getMessage());
            throw new AuthorException(message, e);
        }
    }

    @Override
    @Transactional
    public AuthorDto save(AuthorDto authorDto) throws AuthorException {
        try {
            Author savedAuthor = authorRepository.save(dtoToAuthor(authorDto));
            return authorToDto(savedAuthor);
        } catch (Exception e) {
            String message = "exception while save author in db";
            log.error(message, e.getMessage());
            throw new AuthorException(message, e);
        }
    }

    @Override
    @Transactional
    public AuthorDto update(Long id, AuthorDto authorDto) throws AuthorException {
        try {
            Optional<Author> authorById = authorRepository.findById(id);
            if (authorById.isEmpty()) {
                log.error("author not found by id {} ...", id);
                throw new AuthorNotFoundException(id);
            }
            Author savedAuthor = authorRepository.save(updateAuthor(authorById.get(), authorDto));
            return authorToDto(savedAuthor);
        } catch (Exception e) {
            String message = "exception while update author in db";
            log.error(message, e.getMessage());
            throw new AuthorException(message, e);
        }

    }

    @Override
    @Transactional
    public void delete(Long id) throws AuthorException {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            String message = "exception while delete author in db";
            log.error(message, e.getMessage());
            throw new AuthorException(message, e);
        }
    }

    private AuthorDto authorToDto(Author author) throws AuthorConvertingException {
        if (Objects.isNull(author)) {
            throw new AuthorConvertingException();
        }
        try {
            AuthorDto authorDto = mapper.map(author, AuthorDto.class);
            authorDto.setSongNames(getSongNamesOfAuthor(author));
            return authorDto;
        } catch (Exception e) {
            String message = "error occurred during converting from entity to dto";
            log.error(message, e.getMessage());
            throw new AuthorConvertingException(message, e);
        }
    }

    private List<String> getSongNamesOfAuthor(Author author) {
        return author.getSongs()
                .stream()
                .map(Song::getName)
                .collect(Collectors.toList());
    }

    private Author dtoToAuthor(AuthorDto authorDto) throws AuthorConvertingException {
        if (Objects.isNull(authorDto)) {
            throw new AuthorConvertingException();
        }
        try {
            return mapper.map(authorDto, Author.class);
        } catch (Exception e) {
            String message = "error occurred during converting from dto to entity";
            log.error(message, e.getMessage());
            throw new AuthorConvertingException(message, e);
        }
    }

    private Author updateAuthor(Author author, AuthorDto authorDto) throws AuthorException {
        if (Objects.isNull(authorDto)) {
            String message = "author dto is null";
            log.error(message);
            throw new AuthorException(message);
        }
        author.setId(author.getId());
        author.setFirstname(authorDto.getFirstname());
        author.setLastname(author.getLastname());
        author.setBirthDate(authorDto.getBirthDate());
        return author;
    }
}
