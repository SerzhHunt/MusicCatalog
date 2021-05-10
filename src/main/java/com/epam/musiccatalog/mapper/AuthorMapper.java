package com.epam.musiccatalog.mapper;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toAuthor(AuthorDto authorDto);

    AuthorDto toAuthorDto(Author author);
}
