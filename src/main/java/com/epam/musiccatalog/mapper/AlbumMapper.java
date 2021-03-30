package com.epam.musiccatalog.mapper;

import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.dto.AlbumDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Album.class, AlbumDto.class)
                .field("id", "id")
                .field("name", "name")
                .field("duration", "duration")
                .field("authors", "authors")
                .field("songs", "songs")
                .field("createdDate", "createdDate")
                .byDefault()
                .register();
    }
}
