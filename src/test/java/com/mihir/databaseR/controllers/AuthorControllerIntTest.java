package com.mihir.databaseR.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihir.databaseR.TestDataUtil;
import com.mihir.databaseR.domain.dto.AuthorDto;
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
        AuthorEntity testAuthor = TestDataUtil.createAuthorEntityA();
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
        AuthorEntity testAuthor = TestDataUtil.createAuthorEntityA();
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
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
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

    @Test
    public void testThatFindOneAuthorSuccessfullyReturnHTTP200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
        authorService.saveAuthor(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/"+authorEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindOneAuthorSuccessfullyReturnFoundAuthors() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
        authorService.saveAuthor(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/"+authorEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(46)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Mary Jane")
        );
    }

    @Test
    public void testThatFindOneAuthorFailReturnHTTP404NotFound() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
        authorService.saveAuthor(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/7")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatAuthorFullUpdateSuccessfullyReturnHTTP200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);
        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatAuthorFullUpdateSuccessfullyReturnUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        AuthorEntity author2 = TestDataUtil.createAuthorEntityB();
        author2.setId(authorEntity.getId());

        String authorJson = objectMapper.writeValueAsString(author2);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(author2.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author2.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author2.getName())
        );
    }

    @Test
    public void testThatAuthorFullUpdateFailReturnHTTP404NotFound() throws Exception {
        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatAuthorPartialUpdateSuccessfullyReturnHTTP200Ok() throws Exception {

        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        authorDto.setName("Updated Author");
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatAuthorPartialUpdateSuccessfullyReturnUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorEntityA();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        authorDto.setName("Updated Author");

        String authorJson = objectMapper.writeValueAsString(authorDto);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated Author")
        );
    }
}
