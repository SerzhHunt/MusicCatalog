package com.epam.musiccatalog.repository;

import com.epam.musiccatalog.model.Album;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AlbumRepositoryTests {
    private static final Long ALBUM_ID = 1L;

    @Autowired
    private AlbumRepository repository;

    @Test
    void whenFindByPartNameThenReturnAlbum() {
        Album savedAlbum = repository.save(buildAlbum());

        Album albumByName = repository.findAlbumWithPartOfName("TeS").orElse(new Album());

//        assertEquals(ALBUM_ID, albumByName.getId());
        assertEquals(savedAlbum.getName(), albumByName.getName());
    }

    private Album buildAlbum() {
        return Album.builder()
                .name("AlbumTest")
                .createdDate(LocalDate.now())
                .build();
    }
}
