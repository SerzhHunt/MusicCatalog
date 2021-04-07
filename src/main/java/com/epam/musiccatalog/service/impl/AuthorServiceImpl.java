package com.epam.musiccatalog.service.impl;

import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.dto.AuthorDto;
import com.epam.musiccatalog.repository.AuthorRepository;
import com.epam.musiccatalog.service.AuthorService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("authorService")
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final MapperFacade mapper;

//    @Override
//    @Transactional
//    public List<AuthorDto> getAll() {
//        List<Author> authors = authorRepository.findAll();
//        if (!authors.isEmpty()) {
//            throw new RuntimeException(); //TODO own exception
//        }
//        return authorMapper.mapAsList(authors, AuthorDto.class);
//    }

    @Override
    public List<AuthorDto> getAll() {
        return null;
    }

    @Override
    @Transactional
    public AuthorDto getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return mapper.map(author.get(), AuthorDto.class);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        return null;
    }

    @Override
    public AuthorDto update(Long id, AuthorDto authorDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
//
//    @Override
//    @Transactional
//    public AuthorDto save(AuthorDto authorDto) {
//        if (authorDto != null) {
//            Author author = authorMapper.map(authorDto, Author.class);
//            Author savedAuthor = authorRepository.save(author);//TODO check null?
//            return authorMapper.map(savedAuthor, AuthorDto.class);
//        }
//        return null;//TODO check elements to null, throw exception
//    }
//
//    @Override
//    @Transactional
//    public AuthorDto update(Long id, AuthorDto authorDto) {
//        AuthorDto updateAuthorDto = getAuthorById(id);
//
//        updateAuthorDto.setId(authorDto.getId());
//        updateAuthorDto.setFirstname(authorDto.getFirstname());
//        updateAuthorDto.setLastname(authorDto.getLastname());
//        updateAuthorDto.setBirthDate(authorDto.getBirthDate());
//        updateAuthorDto.setAlbums(authorDto.getAlbums());
//
//        return save(updateAuthorDto);
//    }
//
//    @Override
//    public void delete(Long id) {
//        authorRepository.deleteById(id);
//    }
//}
