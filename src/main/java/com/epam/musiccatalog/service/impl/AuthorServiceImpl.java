package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.repository.AuthorRepository;
import com.epam.musiccatalog.service.AuthorService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("authorService")
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final MapperFacade mapper;

    @Override
    public List<AuthorDto> getAll() {
        return null;
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return mapper.map(author.get(), AuthorDto.class);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        return null;
    }

    @Override
    public AuthorDto update(Long id, AuthorDto authorDto) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }
}
