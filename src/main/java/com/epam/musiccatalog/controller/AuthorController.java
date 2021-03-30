package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "authors")
public class AuthorController {

    private final AuthorServiceImpl service;

    @Autowired
    public AuthorController(AuthorServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("authorId") Long authorId) {
        return new ResponseEntity<>(service.getAuthorById(authorId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody AuthorDto author) {
        return new ResponseEntity<>(service.save(author), HttpStatus.CREATED);
    }

    @PutMapping("{authorId}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("authorId") Long id,
                                                  @RequestBody AuthorDto changedAuthor) {
        return new ResponseEntity<>(service.update(id, changedAuthor), HttpStatus.OK);
    }

    @DeleteMapping("{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable("authorId") Long id) {
        service.delete(id);
    }
}
