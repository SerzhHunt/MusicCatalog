package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.service.impl.AlbumServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("albums")
public class AlbumController {
    private final AlbumServiceImpl service;

    @Autowired
    public AlbumController(AlbumServiceImpl service) {
        this.service = service;
    }

    @GetMapping("all")
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("{albumId}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable("albumId") Long id) {
        return new ResponseEntity<>(service.getAlbumById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AlbumDto> saveAlbum(@RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(service.save(albumDto), HttpStatus.OK);
    }

    @PutMapping("{albumId}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable("albumId") Long id,
                                                @RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(service.update(id, albumDto), HttpStatus.OK);
    }

    @DeleteMapping("{albumId}")
    public void deleteAlbum(@PathVariable("albumId") Long albumId) {
        service.delete(albumId);
    }
}
