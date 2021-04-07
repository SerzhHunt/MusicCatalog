package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.service.impl.SongServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("songs")
@RequiredArgsConstructor
public class SongController {
    private final SongServiceImpl songService;

    @GetMapping("/{albumId}/{songId}")
    public ResponseEntity<SongDto> getSongById(@PathVariable("albumId") Long albumId,
                                               @PathVariable("songId") Long songId) {
        return new ResponseEntity<>(songService.getSongById(albumId, songId), HttpStatus.OK);
    }
}
