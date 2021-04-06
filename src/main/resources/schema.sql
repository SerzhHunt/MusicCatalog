-- ALTER TABLE song DROP CONSTRAINT fk_song_album_id;
-- ALTER TABLE song DROP CONSTRAINT fk_song_author_id;
-- ALTER TABLE album DROP CONSTRAINT fk_song_id;
-- ALTER TABLE author_album DROP CONSTRAINT fk_author_id;
-- ALTER TABLE author_album DROP CONSTRAINT fk_album_id;

DROP TABLE IF EXISTS album;
DROP TABLE IF EXISTS song;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS author_album;

CREATE TABLE author
(
    id INTEGER PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    albums INTEGER
);

CREATE TABLE album
(
    id INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    duration BIGINT NOT NULL,
    created_date DATE NOT NULL,
    authors INTEGER,
    songs INTEGER
);

CREATE TABLE song
(
    id INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    duration BIGINT NOT NULL,
    authors INTEGER,
    album INTEGER
);

CREATE TABLE author_album
(
    album_id INTEGER UNIQUE,
    author_id INTEGER UNIQUE
);

ALTER TABLE author_album ADD CONSTRAINT fk_album_id FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE author_album ADD CONSTRAINT fk_author_id FOREIGN KEY (album_id) REFERENCES album (id)ON UPDATE CASCADE;
ALTER TABLE author_album ADD CONSTRAINT author_album_pkey PRIMARY KEY (album_id,author_id);
ALTER TABLE album ADD CONSTRAINT fk_song_id FOREIGN KEY (songs) REFERENCES song (id);
ALTER TABLE song ADD CONSTRAINT fk_song_author_id FOREIGN KEY (authors) REFERENCES author (id);
ALTER TABLE song ADD CONSTRAINT fk_song_album_id FOREIGN KEY (album) REFERENCES album (id) ON UPDATE CASCADE;