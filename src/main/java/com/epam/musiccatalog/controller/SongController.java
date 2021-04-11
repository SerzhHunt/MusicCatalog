package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.service.impl.SongServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("songs")
public class SongController {
    private final SongServiceImpl songService;

    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<SongDto>> getAllSongs() {
        return new ResponseEntity<>(songService.getAll(), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/{albumId}/{songId}")
    public ResponseEntity<SongDto> getSongById(@PathVariable("albumId") Long albumId,
                                               @PathVariable("songId") Long songId) {
        return new ResponseEntity<>(songService.getSongById(albumId, songId), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("{/albumId}")
    public ResponseEntity<SongDto> saveSong(@PathVariable("albumId") Long albumId,
                                            @RequestBody SongDto songDto) {
        return new ResponseEntity<>(songService.save(albumId, songDto), HttpStatus.CREATED);
    }

    @SneakyThrows
    @PutMapping("{/songId}")
    public ResponseEntity<SongDto> updateSong(@PathVariable("songId") Long id,
                                              @RequestBody SongDto songDto) {
        return new ResponseEntity<>(songService.update(id, songDto), HttpStatus.CREATED);
    }

    @SneakyThrows
    @DeleteMapping("{songId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSong(@PathVariable("songId") Long id) {
        songService.delete(id);
    }
}
