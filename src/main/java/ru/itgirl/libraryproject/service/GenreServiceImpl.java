package ru.itgirl.libraryproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.*;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;
    @Override
    public GenreSearchDto getGenreById (Long id){
        log.info("Trying to find genre by id {}", id);
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()){
            GenreSearchDto genreSearchDto = convertEntityToDto(genre.get());
            log.info("Genre: {}", genreSearchDto.toString());
            return genreSearchDto;
        }
        else {
            log.error("Genre with id {} wasn't found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    private GenreSearchDto convertEntityToDto (Genre genre){
        List<BookNameAndAuthorDto> bookNameAndAuthorDtoList = genre.getBooks()
                .stream()
                .map(book -> BookNameAndAuthorDto.builder()
                        .id(book.getId())
                        .bookName(book.getName())
                        .bookAuthor(book.getAuthors()
                                .stream()
                                .map(author-> {
                                    String an = author.getName();
                                    String as = author.getSurname();
                                    //author.getSurname()
                                    return an + " " + as;
                                }).collect(Collectors.joining()))
                        .build())
                .toList();
        return GenreSearchDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .books(bookNameAndAuthorDtoList)
                .build();
    }
}
