package ru.itgirl.libraryproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject.dto.GenreSearchDto;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreServiceTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    public void testGetGenreById(){
        Long id = 100L;
        String name = "fantasy";
        Set<Book> books = new HashSet<>();
        Genre genre = new Genre(id, name, books);

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));

        GenreSearchDto genreSearchDto = genreService.getGenreById(id);

        verify(genreRepository).findById(id);
        Assertions.assertEquals(genreSearchDto.getId(), genre.getId());
        Assertions.assertEquals(genreSearchDto.getName(), genre.getName());
    }
}
