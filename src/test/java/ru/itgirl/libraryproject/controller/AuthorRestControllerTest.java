package ru.itgirl.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.repository.AuthorRepository;
import ru.itgirl.libraryproject.service.AuthorServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthorRestControllerTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void testGetAuthorById() throws Exception {
        Long id = 1L;
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        authorDto.setName("Александр");
        authorDto.setSurname("Пушкин");

        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testGetAuthorByNameV1() throws Exception{
        String name = "Александр";
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setName(name);
        authorDto.setSurname("Пушкин");

        mockMvc.perform(MockMvcRequestBuilders.get("/author/v1?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));

    }
    @Test
    public void testGetAuthorByNameV2() throws Exception{
        String name = "Александр";
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setName(name);
        authorDto.setSurname("Пушкин");

        mockMvc.perform(MockMvcRequestBuilders.get("/author/v2?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));

    }
    @Test
    public void testGetAuthorByNameV3() throws Exception{
        String name = "Александр";
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setName(name);
        authorDto.setSurname("Пушкин");

        mockMvc.perform(MockMvcRequestBuilders.get("/author/v3?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));

    }
    @Test
    public void testGetAuthorBySurname() throws Exception{
        String surname = "Пушкин";
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setName("Александр");
        authorDto.setSurname(surname);

        mockMvc.perform(MockMvcRequestBuilders.get("/author/s?surname={surname}", surname))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));

    }
    @Test
    public void testCreateAuthor() throws Exception{
        Author author = new Author();
        author.setName("Фредерик");
        author.setSurname("Бакман");

        when(authorRepository.save(any())).thenReturn(true );
        verify(authorRepository.save(author));
        when(authorRepository.findAuthorByName(author.getName())).thenReturn(Optional.of(author));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/author/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(author))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(author.getSurname()));

    }
}