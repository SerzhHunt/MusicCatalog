package com.epam.musiccatalog.controller;

import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.service.impl.AuthorServiceImpl;
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
@RequestMapping(value = "authors")
public class AuthorController {

    private final AuthorServiceImpl authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("authorId") Long authorId) {
        return new ResponseEntity<>(authorService.getAuthorById(authorId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> saveAuthor(@Validated(value = Validation.New.class)
                                                @RequestBody AuthorDto author) {
        return new ResponseEntity<>(authorService.save(author), HttpStatus.CREATED);
    }

    @PutMapping("{authorId}")
    public ResponseEntity<AuthorDto> updateAuthor(@Validated(value = Validation.Exists.class)
                                                  @PathVariable("authorId") Long id,
                                                  @RequestBody AuthorDto changedAuthor) {
        return new ResponseEntity<>(authorService.update(id, changedAuthor), HttpStatus.CREATED);
    }

    @DeleteMapping("{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable("authorId") Long id) {
        authorService.delete(id);
    }
}
