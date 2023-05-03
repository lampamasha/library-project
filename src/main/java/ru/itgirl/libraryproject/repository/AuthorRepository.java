package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.itgirl.libraryproject.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository <Author, Long>, JpaSpecificationExecutor<Author> {
    Optional<Author> findAuthorByName(String name);
    Optional<Author> findAuthorBySurname(String surname);
    @Query(nativeQuery = true, value = "SELECT * FROM author WHERE name = ?")
    Optional<Author> findAuthorByNameBySql(String name);
}