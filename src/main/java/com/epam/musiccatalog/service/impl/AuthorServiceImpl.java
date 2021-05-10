package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.exception.AuthorNotFoundException;
import com.epam.musiccatalog.mapper.AuthorMapper;
import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.repository.AuthorRepository;
import com.epam.musiccatalog.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("authorService")
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthorById(Long id) throws AuthorNotFoundException {
        Optional<Author> author = authorRepository.findById(id);

        if (author.isEmpty()) {
            log.error("author not found by id {} ...", id);
            throw new AuthorNotFoundException(id);
        }
        return authorMapper.toAuthorDto(author.get());
    }

    @Override
    @Transactional
    public AuthorDto save(AuthorDto authorDto) {
        Author savedAuthor = authorRepository.save(authorMapper.toAuthor(authorDto));
        return authorMapper.toAuthorDto(savedAuthor);
    }

    @Override
    @Transactional
    public AuthorDto update(Long id, AuthorDto authorDto) {
        Optional<Author> authorById = authorRepository.findById(id);

        if (authorById.isEmpty()) {
            log.error("author not found by id {} ...", id);
            throw new AuthorNotFoundException(id);
        }

        Author updatedAuthor = authorRepository.save(updateAuthor(authorById.get(), authorDto));
        return authorMapper.toAuthorDto(updatedAuthor);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

//    private AuthorDto authorToDto(Author author) {
//        AuthorDto authorDto = authorMapper.toAuthorDto(author);
//        authorDto.setSongNames(getSongNamesOfAuthor(author));
//        return authorDto;
//    }
//
//    private List<String> getSongNamesOfAuthor(Author author) {
//        return author.getSongs()
//                .stream()
//                .map(Song::getName)
//                .collect(Collectors.toList());
//    }

    private Author updateAuthor(Author author, AuthorDto authorDto) {
        author.setFirstname(authorDto.getFirstname());
        author.setLastname(author.getLastname());
        return author;
    }
}
