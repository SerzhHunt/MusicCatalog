package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.exception.author.AuthorException;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll() throws AuthorException;

    AuthorDto getAuthorById(Long id) throws AuthorException;

    AuthorDto save(AuthorDto authorDto) throws AuthorException;

    AuthorDto update(Long id, AuthorDto authorDto) throws AuthorException;

    void delete(Long id) throws AuthorException;
}
