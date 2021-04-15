package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.service.impl.AlbumServiceImpl;
import com.epam.musiccatalog.transfer.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("albums")
public class AlbumController {
    private final AlbumServiceImpl albumService;

    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        return new ResponseEntity<>(albumService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{albumId}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable("albumId") Long id) {
        return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AlbumDto> saveAlbum(@Validated(value = Validation.New.class)
                                              @RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(albumService.save(albumDto), HttpStatus.CREATED);
    }

    @PutMapping("{albumId}")
    public ResponseEntity<AlbumDto> updateAlbum(@Validated(value = Validation.Exists.class)
                                                @PathVariable("albumId") Long id,
                                                @RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(albumService.update(id, albumDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{albumId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAlbum(@PathVariable("albumId") Long albumId) {
        albumService.delete(albumId);
    }
}
