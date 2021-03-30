package com.epam.musiccatalog.service;

import com.epam.musiccatalog.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();

    AuthorDto getAuthorById(Long id);

    AuthorDto save(AuthorDto authorDto);

    AuthorDto update(Long id, AuthorDto authorDto);

    void delete(Long id);
}
