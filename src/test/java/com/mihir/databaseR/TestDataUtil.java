package com.mihir.databaseR;

import com.mihir.databaseR.domain.dto.AuthorDto;
import com.mihir.databaseR.domain.dto.BookDto;
import com.mihir.databaseR.domain.entities.AuthorEntity;
import com.mihir.databaseR.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static AuthorEntity createAuthorEntity() {
        return AuthorEntity.builder()
                .age(46)
                .id(1L)
                .name("Mary Jane")
                .build();
    }

    public static BookEntity createBookEntity(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-92-95055-02-5")
                .title("Harry Potter")
                .author(author)
                .build();
    }

    public static BookDto createBookDto(final AuthorDto author) {
        return BookDto.builder()
                .isbn("978-92-95055-02-5")
                .title("Harry Potter")
                .author(author)
                .build();
    }
}
