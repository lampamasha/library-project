package ru.itgirl.libraryproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
     @InjectMocks
    private AuthorServiceImpl authorService;

     @Test
    public void testGetAuthorById(){
         Long id = 1L;
         String name = "John";
         String surname = "Doe";
         Set<Book> books = new HashSet<>();
         Author author = new Author(id, name, surname, books);


         when(authorRepository.findById(id)).thenReturn(Optional.of(author));

         AuthorDto authorDto = authorService.getAuthorById(id);

         verify(authorRepository).findById(id);
         Assertions.assertEquals(author.getId(), authorDto.getId());
         Assertions.assertEquals(author.getName(), authorDto.getName());
         Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
     }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));

        verify(authorRepository).findById(id);
    }
}
