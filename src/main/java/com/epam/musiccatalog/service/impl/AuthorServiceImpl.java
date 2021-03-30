package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.mapper.AuthorMapper;
import com.epam.musiccatalog.repository.AuthorRepository;
import com.epam.musiccatalog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("authorService")
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    @Transactional
    public List<AuthorDto> getAll() {
        List<Author> authors = authorRepository.findAll();
        if (!authors.isEmpty()) {
            throw new RuntimeException(); //TODO own exception
        }
        return authorMapper.mapAsList(authors, AuthorDto.class);
    }

    @Override
    @Transactional
    public AuthorDto getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new RuntimeException(); //TODO own exception
        }
        return authorMapper.map(author.get(), AuthorDto.class);
    }

    @Override
    @Transactional
    public AuthorDto save(AuthorDto authorDto) {
        if (authorDto != null) {
            Author author = authorMapper.map(authorDto, Author.class);
            Author savedAuthor = authorRepository.save(author);//TODO check null?
            return authorMapper.map(savedAuthor, AuthorDto.class);
        }
        return null;//TODO check elements to null, throw exception
    }

    @Override
    @Transactional
    public AuthorDto update(Long id, AuthorDto authorDto) {
        AuthorDto updateAuthorDto = getAuthorById(id);

        updateAuthorDto.setId(authorDto.getId());
        updateAuthorDto.setFirstname(authorDto.getFirstname());
        updateAuthorDto.setLastname(authorDto.getLastname());
        updateAuthorDto.setBirthDate(authorDto.getBirthDate());
        updateAuthorDto.setAlbums(authorDto.getAlbums());

        return save(updateAuthorDto);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
