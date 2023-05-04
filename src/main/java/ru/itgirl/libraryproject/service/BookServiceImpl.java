package ru.itgirl.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.*;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public BookDto getBookByNameV1(String name) {
        log.info("Trying to find book by its name {} version 1", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        }
        else {
            log.error("No such book with name {}", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto getBookByNameV2(String name){
        log.info("Trying to find book by its name {} version 2", name);
        Optional<Book> book = bookRepository.findBookByNameBySql(name);
        if (book.isPresent()){
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        }
        else {
            log.error("No such book with name {}", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto getBookByNameV3(String name) {
        log.info("Trying to find book by its name {} version 3", name);
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Optional<Book> bookOpt = bookRepository.findOne(specification);
        if (bookOpt.isPresent()) {
            BookDto bookDto = convertEntityToDto(bookOpt.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        }
        else {
            log.error("No such book with name {}", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto createBook (BookCreateDto bookCreateDto){
        log.info("Trying to create new book by name and genre {}", bookCreateDto.toString());
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        Optional<Book> bookOpt = bookRepository.findBookByName(bookCreateDto.getName());
        if (bookOpt.isPresent()){
            BookDto bookDto = convertEntityToDto(bookOpt.get());
            log.info("Book successfully created and saved: {}", bookDto.toString());
            return bookDto;
        }
        else {
            log.error("Book {} wasn't created", bookCreateDto.toString());
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto updateBook (BookUpdateDto bookUpdateDto){
        log.info("Trying to update book's data to {}", bookUpdateDto.toString());
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        Book savedBook = bookRepository.save(book);
        Optional<Book> bookOpt = bookRepository.findBookByName(bookUpdateDto.getName());
        if (bookOpt.isPresent()){
            BookDto bookDto = convertEntityToDto(bookOpt.get());
            log.info("Book data successfully updated and saved: {}", bookDto.toString());
            return bookDto;
        }
        else {
            log.error("Book data {} wasn't updated", bookUpdateDto.toString());
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public void deleteBook(Long id){
        log.info("Trying to delete book by id {}", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            log.info("Book with id {} successfully deleted", id);
            bookRepository.deleteById(id);
        }
        else {
            log.error("Book with id {} wasn't deleted", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public List<BookDto> getAllBooks(){
        log.info("Trying to get list of books");
        List<Book> books  = bookRepository.findAll();
        if (books.isEmpty()){
            log.error("There is no books in the database");
            throw new NoSuchElementException("No value present");
        }
        else{
            log.info("There is some books in the database");
            return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        }
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto){
        Genre genre = Genre.builder()
                .name(bookCreateDto.getGenre())
                .build();
        if (Objects.equals(genreRepository.findGenreByNameBySql(bookCreateDto.getGenre()), null)){
            genreRepository.save(genre);
        }
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genreRepository.findById(genreRepository.findGenreByNameBySql(bookCreateDto.getGenre())).get())
                .build();
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }
}