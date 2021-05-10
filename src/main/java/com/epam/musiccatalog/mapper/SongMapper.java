package com.epam.musiccatalog.mapper;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.model.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    Song toSong(SongDto songDto);

    SongDto toSongDto(Song song);
}
