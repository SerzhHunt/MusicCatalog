package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.exception.AlbumNotFoundException;
import com.epam.musiccatalog.service.impl.SongServiceImpl;
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
@RequestMapping("songs")
public class SongController {
    private final SongServiceImpl songService;

    @GetMapping
    public ResponseEntity<List<SongDto>> getAllSongs() {
        return new ResponseEntity<>(songService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{albumId}/{songId}")
    public ResponseEntity<SongDto> getSongById(@PathVariable("albumId") Long albumId,
                                               @PathVariable("songId") Long songId) {
        return new ResponseEntity<>(songService.getSongById(albumId, songId), HttpStatus.OK);
    }

    @PostMapping("{/albumId}")
    public ResponseEntity<SongDto> saveSong(@Validated(value = Validation.New.class)
                                            @PathVariable("albumId") Long albumId,
                                            @RequestBody SongDto songDto) throws AlbumNotFoundException {
        return new ResponseEntity<>(songService.save(albumId, songDto), HttpStatus.CREATED);
    }

    @PutMapping("{/songId}")
    public ResponseEntity<SongDto> updateSong(@Validated(value = Validation.Exists.class)
                                              @PathVariable("songId") Long id,
                                              @RequestBody SongDto songDto) {
        return new ResponseEntity<>(songService.update(id, songDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{songId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSong(@PathVariable("songId") Long id) {
        songService.delete(id);
    }
}
