package com.epam.musiccatalog.mapper;

import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    Album toAlbum(AlbumDto albumDto);

    AlbumDto toAlbumDto(Album album);
}
