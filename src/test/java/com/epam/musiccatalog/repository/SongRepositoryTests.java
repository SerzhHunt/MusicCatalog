package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.Album;
import com.epam.musiccatalog.model.Author;
import com.epam.musiccatalog.model.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SongRepositoryTests {
    private static final Long SONG_ID = 1L;
    private static final Long ALBUM_ID = 1L;
    private static final Long AUTHOR_ID = 1L;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AuthorRepository authorRepository;

/*    @Test
    void whenFindSongByIdAndAlbumIdThenReturnSong() {
        Author savedAuthor = authorRepository.save(buildAuthor());
        Album savedAlbum = albumRepository.save(buildAlbum());
        songRepository.save(buildSong(savedAlbum, Collections.singletonList(savedAuthor)));

        Song songById = songRepository.findSongByIdInAlbumById(ALBUM_ID, SONG_ID).orElse(new Song());

//        assertEquals(SONG_ID, songById.getId());
//        assertEquals(ALBUM_ID, songById.getAlbum().getId());
//        assertEquals(AUTHOR_ID, songById.getAuthors().get(0).getId());
        assertEquals(savedAuthor.getFirstname(), songById.getAuthors().get(0).getFirstname());
        assertEquals(savedAlbum.getName(), songById.getAlbum().getName());

    }*/

    private Author buildAuthor() {
        return Author.builder()
                .firstname("firstName")
                .lastname("lastName")
                .birthDate(LocalDate.of(1980, 1, 1))
                .build();
    }

    private Album buildAlbum() {
        return Album.builder()
                .name("AlbumTest")
                .createdDate(LocalDate.now())
                .build();
    }

    private Song buildSong(Album album, List<Author> authors) {
        return Song.builder()
                .name("SongTest")
                .duration(Duration.ofMinutes(5))
                .album(album)
                .authors(authors)
                .build();
    }
}
