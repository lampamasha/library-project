package ru.itgirl.libraryproject.service;

import ru.itgirl.libraryproject.dto.BookCreateDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto getBookByNameV1(String name);
    BookDto getBookByNameV2(String name);
    BookDto getBookByNameV3(String name);
    BookDto createBook(BookCreateDto bookCreateDto);
    BookDto updateBook(BookUpdateDto bookUpdateDto);
    void deleteBook(Long id);
    List<BookDto> getAllBooks();
}