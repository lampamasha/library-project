package ru.itgirl.libraryproject.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.libraryproject.dto.AuthorCreateDto;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject.service.AuthorService;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class AuthorRestController {
    private final AuthorService authorService;

    @GetMapping("/author/v1")
    AuthorDto getAuthorByNameV1(@RequestParam("name") String name){
        return authorService.getAuthorByNameV1(name);
    }

    @GetMapping("/author/s")
    AuthorDto getAuthorBySurname(@RequestParam("surname") String surname){
        return authorService.getAuthorBySurnameV1(surname);
    }

    @GetMapping("/author/v2")
    AuthorDto getAuthorByNameV2(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV2(name);
    }

    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id){
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author/v3")
    AuthorDto getAuthorByNameV3(@RequestParam("name") String name){
        return authorService.getAuthorByNameV3(name);
    }

    @PostMapping("/author/create")
    AuthorDto createAuthor(@RequestBody @Valid AuthorCreateDto authorCreateDto){
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/update")
    AuthorDto updateAuthor(@RequestBody @Valid AuthorUpdateDto authorUpdateDto){
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping("/author/delete/{id}")
    void deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthor(id);
    }
}
