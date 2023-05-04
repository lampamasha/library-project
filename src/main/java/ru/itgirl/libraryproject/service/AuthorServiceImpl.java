package ru.itgirl.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.AuthorCreateDto;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorByNameV1(String name){
        log.info("Trying to find author by the name {} version 1", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()){
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        }
        else {
            log.error("No such Author with name {}", name);
            throw new NoSuchElementException("No value present");
        }
    }
    @Override
    public AuthorDto getAuthorBySurnameV1(String surname){
        log.info("Trying to find author by the surname {} ", surname);
        Optional<Author> author = authorRepository.findAuthorBySurname(surname);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        }
        else {
            log.error("No such Author with surname {}", surname);
            throw new NoSuchElementException("No value present");
        }
    }
    @Override
    public AuthorDto getAuthorByNameV3(String name){
        log.info("Trying to find author by the name {} version 3", name);
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name"),name);
            }
        });

        Optional<Author> author = authorRepository.findOne(specification);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        }
        else {
            log.error("No such Author with name {}", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name){
        log.info("Trying to find author by the name {} version 2", name);
        Optional<Author> author = authorRepository.findAuthorByNameBySql(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        }
        else {
            log.error("No such Author with name {}", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getAuthorById(Long id){
        log.info("Trying to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        }
        else {
            log.error("Author with id {} wasn't found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto){
        log.info("Trying to create new author by name and surname {}", authorCreateDto.toString());
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        Optional<Author> authorOpt = authorRepository.findAuthorByName(authorCreateDto.getName());
        if (authorOpt.isPresent()){
            AuthorDto authorDto = convertEntityToDto(authorOpt.get());
            log.info("Author successfully created and saved: {}", authorDto.toString());
            return authorDto;
        }
        else {
            log.error("Author {} wasn't created", authorCreateDto.toString());
            throw new NoSuchElementException("No value present");
        }
    }
    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto){
        log.info("Trying to update author's data to {}", authorUpdateDto.toString());
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRepository.save(author);
        Optional<Author> authorOpt = authorRepository.findAuthorByName(authorUpdateDto.getName());
        if (authorOpt.isPresent()){
            AuthorDto authorDto = convertEntityToDto(authorOpt.get());
            log.info("Author's data successfully updated and saved: {}", authorDto.toString());
            return authorDto;
        }
        else {
            log.error("Author's data {} wasn't updated", authorUpdateDto.toString());
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public void deleteAuthor(Long id){
        log.info("Trying to delete author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            log.info("Author with id {} successfully deleted", id);
            authorRepository.deleteById(id);
        }
        else {
            log.error("Author with id {} wasn't deleted", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public List<AuthorDto> getAllAuthors(){
        log.info("Trying to get list of authors");
        List<Author> authors  = authorRepository.findAll();
        if (authors.isEmpty()){
            log.error("There is no Authors in the database");
            throw new NoSuchElementException("No value present");
        }
        else {
            log.info("There is some Authors in the database");
            return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        }
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto){
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
    }
}