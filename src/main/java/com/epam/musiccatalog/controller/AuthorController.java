package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "authors")
public class AuthorController {

    private final AuthorServiceImpl authorService;

    @SneakyThrows
    @GetMapping
    @ApiOperation(value = "View a list of available authors", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("authorId") Long authorId) {
        return new ResponseEntity<>(authorService.getAuthorById(authorId), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody AuthorDto author) {
        return new ResponseEntity<>(authorService.save(author), HttpStatus.CREATED);
    }

    @SneakyThrows
    @PutMapping("{authorId}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("authorId") Long id,
                                                  @RequestBody AuthorDto changedAuthor) {
        return new ResponseEntity<>(authorService.update(id, changedAuthor), HttpStatus.OK);
    }

    @SneakyThrows
    @DeleteMapping("{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable("authorId") Long id) {
        authorService.delete(id);
    }
}
