package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
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
