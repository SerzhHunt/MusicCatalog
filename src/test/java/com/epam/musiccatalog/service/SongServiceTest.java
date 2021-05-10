package com.epam.musiccatalog.service;

import com.epam.musiccatalog.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {
    private static final Long AUTHOR_ID = 1L;

    @Mock
    private SongRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getSongById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}