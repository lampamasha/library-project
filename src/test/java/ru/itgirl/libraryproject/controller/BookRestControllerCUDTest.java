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
import ru.itgirl.libraryproject.dto.*;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.service.BookServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerCUDTest {

    @Mock
    private BookRepository bookRepository;

    @MockBean
    private BookServiceImpl bookService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

   @Test
    public void testCreateBook() throws Exception{
        String name = "Harry Potter";
        String genre = "fantasy";
        Long id = 1L;
        BookCreateDto bookCreateDto = new BookCreateDto(name, genre);
        BookDto bookDto = new BookDto(id, name, genre);

        Mockito.when(bookService.createBook(Mockito.any())).thenReturn(bookDto);
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(bookDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/book/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(bookCreateDto))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookCreateDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookCreateDto.getGenre()));
    }
    @Test
    public void testUpdateBook() throws Exception{
        String name = "Harry Potter";
        String genre = "fantasy";
        Long id = 100L;
        BookUpdateDto bookUpdateDto = new BookUpdateDto(id, name);
        BookDto bookDto = new BookDto(id, name, genre);

        Mockito.when(bookService.updateBook(Mockito.any())).thenReturn(bookDto);
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(bookDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/book/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(bookUpdateDto))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookUpdateDto.getName()));
    }

    @Test
    public void testDeleteBook() throws Exception{
        Long id = 100L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/{id}", id))
                .andExpect(status().isOk());
    }
}
