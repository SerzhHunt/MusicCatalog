package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.SongDto;
import com.epam.musiccatalog.service.impl.SongServiceImpl;
import com.epam.musiccatalog.valid.Validation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "song", description = "the Song API")
@RestController
@RequiredArgsConstructor
@RequestMapping("songs")
public class SongController {
    private final SongServiceImpl songService;

    @Operation(summary = "Find all songs", tags = {"song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @GetMapping
    public ResponseEntity<List<SongDto>> getAllSongs() {
        return new ResponseEntity<>(songService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a song by name in album by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the song"),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "404", description = "Song not found")})
    @GetMapping("/{albumName}/{songId}")
    public ResponseEntity<SongDto> getSongById(@Parameter(description = "Album name to be searched")
                                               @PathVariable("albumName") String albumName,
                                               @Parameter(description = "Id of song to be searched")
                                               @PathVariable("songId") Long songId) {
        return new ResponseEntity<>(songService.getSongById(albumName, songId), HttpStatus.OK);
    }

    @Operation(summary = "Add new song", tags = {"song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Song created"),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "409", description = "Song already exists"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PostMapping("/{albumName}/")
    public ResponseEntity<SongDto> saveSong(@Parameter(description = "Album name to be searched")
                                            @PathVariable("albumName") String albumName,
                                            @Validated(value = Validation.OnCreate.class)
                                            @RequestBody SongDto songDto) {
        return new ResponseEntity<>(songService.save(albumName, songDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing song", tags = {"song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Validation exception"),
            @ApiResponse(responseCode = "404", description = "Song not found"),
            @ApiResponse(responseCode = "405", description = "Method not allowed"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping("/{songId}")
    public ResponseEntity<SongDto> updateSong(@Parameter(description = "Id of song to be searched")
                                              @PathVariable("songId") Long id,
                                              @Parameter(description = "Updated song")
                                              @Validated(value = Validation.OnUpdate.class)
                                              @RequestBody SongDto songDto) {
        return new ResponseEntity<>(songService.update(id, songDto), HttpStatus.OK);
    }

    @Operation(summary = "Deletes a song", tags = {"song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Song not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @DeleteMapping("{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@Parameter(description = "Id of song to be searched")
                           @PathVariable("songId") Long id) {
        songService.delete(id);
    }
}
