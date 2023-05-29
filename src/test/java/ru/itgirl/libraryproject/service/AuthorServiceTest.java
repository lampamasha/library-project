package ru.itgirl.libraryproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject.dto.AuthorCreateDto;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

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
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());

    }
    @Test
    public void testGetAuthorByIdFailed(){
        Long id = 1L;

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, ()-> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testGetAuthorByNameV1(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV1(name);
        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameV1Failed(){
        String name = "John";

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, ()-> authorService.getAuthorByNameV1(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void testGetAuthorBySurnameV1(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorBySurname(surname)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorBySurnameV1(surname);
        verify(authorRepository).findAuthorBySurname(surname);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorBySurnameV1Failed(){
        String surname = "Doe";

        when(authorRepository.findAuthorBySurname(surname)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, ()-> authorService.getAuthorBySurnameV1(surname));
        verify(authorRepository).findAuthorBySurname(surname);
    }
    @Test
    public void testGetAuthorByNameV2(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV2(name);
        verify(authorRepository).findAuthorByNameBySql(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameV2Failed(){
        String name = "John";

        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, ()-> authorService.getAuthorByNameV2(name));
        verify(authorRepository).findAuthorByNameBySql(name);
    }

    @Test
    public void testCreateAuthor(){
        Long id = 100L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        AuthorCreateDto authorCreateDto = new AuthorCreateDto(name, surname);

        when(authorRepository.save(Mockito.any())).thenReturn(author);
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.createAuthor(authorCreateDto);

        verify(authorRepository).save(Mockito.any(Author.class));
        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
        Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
    }

    @Test
    public void testUpdateAuthor(){
        Long id = 100L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(id, name, surname);

        when(authorRepository.save(Mockito.any())).thenReturn(author);
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));


        AuthorDto authorDto = authorService.updateAuthor(authorUpdateDto);

        verify(authorRepository).save(Mockito.any(Author.class));
        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
        Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
    }

    @Test
    public void testDeleteAuthor(){
        Long id = 100L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        authorService.deleteAuthor(id);

        verify(authorRepository).deleteById(id);
    }

    @Test
    public void testGetAllAuthors(){
        Long id = 100L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        List<Author> authors = Collections.singletonList(author);

        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorDto> authorsDto = authorService.getAllAuthors();

        verify(authorRepository).findAll();
        Assertions.assertNotNull(authorsDto);
        Assertions.assertEquals(authors.size(), authorsDto.size());

        AuthorDto authorDto = authorsDto.get(0);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
        Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
    }
}
