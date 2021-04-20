package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AlbumDto;
import com.epam.musiccatalog.service.impl.AlbumServiceImpl;
import com.epam.musiccatalog.transfer.Validation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "album", description = "the Album API")
@RestController
@RequiredArgsConstructor
@RequestMapping("albums")
public class AlbumController {
    private final AlbumServiceImpl albumService;

    @Operation(summary = "Find all albums", tags = {"album"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = AlbumDto.class)))),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        return new ResponseEntity<>(albumService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a album by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the album",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlbumDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Album not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @GetMapping("{albumId}")
    public ResponseEntity<AlbumDto> getAlbumById(@Parameter(description = "id of album to be searched")
                                                 @PathVariable("albumId") Long id) {
        return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
    }

    @Operation(summary = "Add new album", tags = {"album"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Album created",
                    content = @Content(schema = @Schema(implementation = AlbumDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Album already exists"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PostMapping
    public ResponseEntity<AlbumDto> saveAlbum(@Validated(value = Validation.New.class)
                                              @RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(albumService.save(albumDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing album", tags = {"album"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Album not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping("{albumId}")
    public ResponseEntity<AlbumDto> updateAlbum(@Validated(value = Validation.Exists.class)
                                                @PathVariable("albumId") Long id,
                                                @RequestBody AlbumDto albumDto) {
        return new ResponseEntity<>(albumService.update(id, albumDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Deletes a album", tags = {"album"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Album not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @DeleteMapping("{albumId}")
    public void deleteAlbum(@PathVariable("albumId") Long albumId) {
        albumService.delete(albumId);
    }
}
