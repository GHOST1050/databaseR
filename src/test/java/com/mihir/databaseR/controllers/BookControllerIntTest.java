package com.mihir.databaseR.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihir.databaseR.TestDataUtil;
import com.mihir.databaseR.domain.dto.BookDto;
import com.mihir.databaseR.domain.entities.BookEntity;
import com.mihir.databaseR.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntTest {

    private final BookService  bookService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntTest(BookService bookService, MockMvc mockMvc) {
        this.bookService = bookService;
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnHTTP201Created() throws Exception{
        BookDto bookDto = TestDataUtil.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnCreatedBook() throws Exception{
        BookDto bookDto = TestDataUtil.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatFindAllBookSuccessfullyReturnHTTP200Ok() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindAllBookSuccessfullyReturnListOfBook() throws Exception{
        BookEntity bookEntity = TestDataUtil.createBookEntityA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].title").value(bookEntity.getTitle())
        );
    }

    @Test
    public void testThatFindOneBookSuccessfullyReturnHTTP200Ok() throws Exception{

        BookEntity bookEntity = TestDataUtil.createBookEntityA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindOneBookSuccessfullyReturnTargetBook() throws Exception{
        BookEntity bookEntity = TestDataUtil.createBookEntityA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+bookEntity.getIsbn())

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntity.getTitle())
        );
    }

    @Test
    public void testThatFindOneBookFailReturnHTTP404NotFound() throws Exception{

        BookEntity bookEntity = TestDataUtil.createBookEntityA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/5")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatBookFullUpdateSuccessfullyReturnHTTP200Ok() throws Exception{

        BookEntity bookEntityA = TestDataUtil.createBookEntityA(null);
        BookEntity updateBook = bookService.createUpdateBook(bookEntityA.getIsbn(), bookEntityA);

        BookDto bookDtoA = TestDataUtil.createBookDtoA(null);
        bookDtoA.setIsbn(updateBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDtoA);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatBookFullUpdateSuccessfullyReturnUpdatedBook() throws Exception{
        BookEntity bookEntityA = TestDataUtil.createBookEntityA(null);
        BookEntity updateBook = bookService.createUpdateBook(bookEntityA.getIsbn(), bookEntityA);

        BookDto bookDtoB = TestDataUtil.createBookDtoB(null);
        bookDtoB.setIsbn(updateBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+bookDtoB.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDtoB.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDtoB.getTitle())
        );

    }

}
