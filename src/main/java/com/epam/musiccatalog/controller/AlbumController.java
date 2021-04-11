package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.service.impl.AlbumServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("albums")
public class AlbumController {
    private final AlbumServiceImpl albumService;

    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        return new ResponseEntity<>(albumService.getAll(), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("{albumId}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable("albumId") Long id) {
        return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<AlbumDto> saveAlbum(@RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(albumService.save(albumDto), HttpStatus.OK);
    }

    @SneakyThrows
    @PutMapping("{albumId}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable("albumId") Long id,
                                                @RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(albumService.update(id, albumDto), HttpStatus.OK);
    }

    @SneakyThrows
    @DeleteMapping("{albumId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAlbum(@PathVariable("albumId") Long albumId) {
        albumService.delete(albumId);
    }
}
