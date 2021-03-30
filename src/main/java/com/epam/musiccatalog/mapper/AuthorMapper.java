package com.epam.musiccatalog.mapper;

import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.dto.AuthorDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Author.class, AuthorDto.class)
                .field("id", "id")
                .field("firstname", "firstname")
                .field("lastname", "lastname")
                .field("birthDate", "birthDate")
                .field("albums", "albums")
                .byDefault()
                .register();
    }
}
