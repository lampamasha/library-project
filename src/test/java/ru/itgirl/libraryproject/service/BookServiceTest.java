package ru.itgirl.libraryproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject.dto.*;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private GenreRepository genreRepository;

   @Test
    public void testGetBookByNameV1() {
        Long id = 1L;
        String name = "Harry Potter";
        Genre genre = new Genre();
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, genre, authors);

        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getBookByNameV1(name);

        verify(bookRepository).findBookByName(name);
        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
   }

    @Test
    public void testGetBookByNameV1Failed(){
       String name = "Harry Potter";

       when(bookRepository.findBookByName(name)).thenReturn(Optional.empty());

       Assertions.assertThrows(NoSuchElementException.class, () -> bookService.getBookByNameV1(name));
       verify(bookRepository).findBookByName(name);
    }

    @Test
    public void testGetBookByNameV2() {
        Long id = 1L;
        String name = "Harry Potter";
        Genre genre = new Genre();
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, genre, authors);

        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getBookByNameV2(name);

        verify(bookRepository).findBookByNameBySql(name);
        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
    }

    @Test
    public void testGetBookByNameV2Failed(){
        String name = "Harry Potter";

        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> bookService.getBookByNameV2(name));
        verify(bookRepository).findBookByNameBySql(name);
    }

    @Test
    public void testCreateBook(){
        Long id = 100L;
        String name = "Harry Potter";
        String genreName = "fantasy";
        Genre genre = new Genre(100L, genreName, new HashSet<>());
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, genre, authors);
        BookCreateDto bookCreateDto = new BookCreateDto(name, genreName);

        when(bookRepository.save(Mockito.any())).thenReturn(book);
        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));
        when(genreRepository.findGenreByNameBySql(genreName)).thenReturn(id);
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));

        BookDto bookDto = bookService.createBook(bookCreateDto);

        verify(bookRepository).save(Mockito.any(Book.class));
        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getName(), bookDto.getName());
    }

    @Test
    public void testUpdateAuthor(){
        Long id = 100L;
        String name = "Harry Potter";
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, new Genre(), authors);
        BookUpdateDto bookUpdateDto = new BookUpdateDto(id, name);

        when(bookRepository.save(Mockito.any())).thenReturn(book);
        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.updateBook(bookUpdateDto);

        verify(bookRepository).save(Mockito.any(Book.class));
        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getName(), bookDto.getName());
    }

    @Test
    public void testDeleteBook(){
        Long id = 100L;
        String name = "Harry Potter";
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, new Genre(), authors);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        bookService.deleteBook(id);

        verify(bookRepository).deleteById(id);
    }

    @Test
    public void testGetAllBooks(){
        Long id = 100L;
        String name = "John";
        String genreName = "fantasy";
        Genre genre = new Genre(100L, genreName, new HashSet<>());
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, genre, authors);
        List<Book> books = Collections.singletonList(book);

        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> booksDto = bookService.getAllBooks();

        verify(bookRepository).findAll();
        Assertions.assertNotNull(booksDto);
        Assertions.assertEquals(books.size(), booksDto.size());

        BookDto bookDto = booksDto.get(0);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getName(), bookDto.getName());
    }
}
