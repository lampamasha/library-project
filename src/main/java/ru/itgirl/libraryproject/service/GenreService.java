package ru.itgirl.libraryproject.service;

import ru.itgirl.libraryproject.dto.GenreSearchDto;

public interface GenreService {
    GenreSearchDto getGenreById(Long id);
}
