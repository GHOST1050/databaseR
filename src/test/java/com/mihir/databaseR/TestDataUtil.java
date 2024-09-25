package com.mihir.databaseR;

import com.mihir.databaseR.domain.dto.AuthorDto;
import com.mihir.databaseR.domain.dto.BookDto;
import com.mihir.databaseR.domain.entities.AuthorEntity;
import com.mihir.databaseR.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static AuthorEntity createAuthorEntityA() {
        return AuthorEntity.builder()
                .age(46)
                .id(1L)
                .name("Mary Jane")
                .build();
    }

    public static AuthorEntity createAuthorEntityB() {
        return AuthorEntity.builder()
                .age(62)
                .id(2L)
                .name("JK Rowling")
                .build();
    }

    public static BookEntity createBookEntityA(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-92-95055-02-5")
                .title("Harry Potter")
                .author(author)
                .build();
    }

    public static BookEntity createBookEntityB(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-6-6502-5654-7")
                .title("Animal Farm")
                .author(author)
                .build();
    }

    public static BookDto createBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                .isbn("978-92-95055-02-5")
                .title("Harry Potter")
                .author(author)
                .build();
    }

    public static BookDto createBookDtoB(final AuthorDto author) {
        return BookDto.builder()
                .isbn("978-6-6502-5654-7")
                .title("Animal Farm")
                .author(author)
                .build();
    }

    public static AuthorDto createAuthorDto() {
        return AuthorDto.builder()
                .age(46)
                .id(1L)
                .name("Mary Jane")
                .build();
    }
}
