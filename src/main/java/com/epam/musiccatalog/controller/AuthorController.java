package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
import com.epam.musiccatalog.transfer.Validation;
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

@Tag(name = "author", description = "the Author API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "authors")
public class AuthorController {

    private final AuthorServiceImpl authorService;

    @Operation(summary = "Find all authors", tags = {"author"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a author by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the author"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Author not found")})
    @GetMapping("{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@Parameter(description = "id of author to be searched")
                                                   @PathVariable("authorId") Long authorId) {
        return new ResponseEntity<>(authorService.getAuthorById(authorId), HttpStatus.OK);
    }

    @Operation(summary = "Add new author", tags = {"author"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Author already exists"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PostMapping
    public ResponseEntity<AuthorDto> saveAuthor(@Validated(value = Validation.New.class)
                                                @RequestBody AuthorDto author) {
        return new ResponseEntity<>(authorService.save(author), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing author", tags = {"author"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping("{authorId}")
    public ResponseEntity<AuthorDto> updateAuthor(@Validated(value = Validation.Exists.class)
                                                  @PathVariable("authorId") Long id,
                                                  @RequestBody AuthorDto changedAuthor) {
        return new ResponseEntity<>(authorService.update(id, changedAuthor), HttpStatus.OK);
    }

    @Operation(summary = "Deletes a author", tags = {"author"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @DeleteMapping("{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@Parameter(description = "id of author to be searched")
                             @PathVariable("authorId") Long id) {
        authorService.delete(id);
    }
}
