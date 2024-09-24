package com.mihir.databaseR.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihir.databaseR.TestDataUtil;
import com.mihir.databaseR.domain.entities.AuthorEntity;
import com.mihir.databaseR.service.AuthorService;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntTest {

    private final AuthorService authorService;

    private final ObjectMapper objectMapper;

    private final MockMvc mockMvc;

    @Autowired
    public AuthorControllerIntTest(AuthorService authorService, MockMvc mockMvc) {
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
        this.mockMvc = mockMvc;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnHTTP201Created() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createAuthorEntity();
        testAuthor.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnSavedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createAuthorEntity();
        testAuthor.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(46)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Mary Jane")
        );
    }

    @Test
    public void testThatFindAllAuthorSuccessfullyReturnHTTP200Ok() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindAllAuthorSuccessfullyReturnListOfAuthors() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntity();
        authorService.saveAuthor(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].age").value(46)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].name").value("Mary Jane")
        );
    }
}
