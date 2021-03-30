package com.epam.musiccatalog.mapper;

import com.epam.musiccatalog.model.Song;
import com.epam.musiccatalog.dto.SongDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class SongMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Song.class, SongDto.class)
                .field("id", "id")
                .field("name", "name")
                .field("duration", "duration")
                .field("authors", "authors")
                .field("album", "album")
                .byDefault()
                .register();
    }
}
