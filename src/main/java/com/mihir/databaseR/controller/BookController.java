package com.mihir.databaseR.controller;

import com.mihir.databaseR.domain.dto.BookDto;
import com.mihir.databaseR.domain.entities.BookEntity;
import com.mihir.databaseR.mapper.Mapper;
import com.mihir.databaseR.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final Mapper<BookEntity, BookDto> bookMapper;

    private final BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity saveBookEntity = bookService.createBook(isbn, bookEntity);
        BookDto saveBookDto = bookMapper.mapTo(saveBookEntity);
        return new ResponseEntity<>(saveBookDto, HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        List<BookEntity> bookEntities = bookService.findAll();
        return bookEntities.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> bookEntity = bookService.findOne(isbn);
        return bookEntity.map(bookEntity1 -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity1);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
