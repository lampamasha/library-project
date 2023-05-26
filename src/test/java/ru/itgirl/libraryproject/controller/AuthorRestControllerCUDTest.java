package ru.itgirl.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.libraryproject.dto.AuthorCreateDto;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.repository.AuthorRepository;
import ru.itgirl.libraryproject.service.AuthorServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthorRestControllerCUDTest {
    @Mock
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorServiceImpl authorService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void testCreateAuthor() throws Exception{
        String name = "John";
        String surname = "Doe";
        Long id = 1L;
        List<BookDto> booksDto = new ArrayList<>();
        AuthorCreateDto authorCreateDto = new AuthorCreateDto(name, surname);
        AuthorDto authorDto = new AuthorDto(id, name, surname, booksDto);

        Mockito.when(authorService.createAuthor(Mockito.any())).thenReturn(authorDto);
        Mockito.when(authorRepository.save(Mockito.any())).thenReturn(authorDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/author/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authorDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(authorCreateDto))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorCreateDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorCreateDto.getSurname()));
    }
    @Test
    public void testUpdateAuthor() throws Exception{
        String name = "John";
        String surname = "Doe";
        Long id = 1L;
        List<BookDto> booksDto = new ArrayList<>();
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(id, name, surname);
        AuthorDto authorDto = new AuthorDto(id, name, surname, booksDto);

        Mockito.when(authorService.updateAuthor(Mockito.any())).thenReturn(authorDto);
        Mockito.when(authorRepository.save(Mockito.any())).thenReturn(authorDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/author/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authorDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(authorUpdateDto))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorUpdateDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorUpdateDto.getSurname()));
    }

    @Test
    public void testDeleteAuthor() throws Exception{
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/author/delete/{id}", id))
                .andExpect(status().isOk());
    }
}
