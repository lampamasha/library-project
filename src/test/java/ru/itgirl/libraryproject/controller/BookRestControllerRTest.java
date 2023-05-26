package ru.itgirl.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.service.BookServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerRTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl authorService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    @Test
    public void testGetBookByNameV1() throws Exception {
        String name = "Война и мир";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(name);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v1?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));

    }

    @Test
    public void testGetBookByNameV2() throws Exception {
        String name = "Война и мир";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(name);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v2?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));

    }

    @Test
    public void testGetBookByNameV3() throws Exception {
        String name = "Война и мир";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(name);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v3?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));

    }
}