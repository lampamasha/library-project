package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itgirl.libraryproject.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(nativeQuery=true, value="SELECT id FROM genre WHERE name=?")
    Long findGenreByNameBySql(String name);
}