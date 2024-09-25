package com.mihir.databaseR.service.impl;

import com.mihir.databaseR.domain.entities.BookEntity;
import com.mihir.databaseR.repositories.BookRepository;
import com.mihir.databaseR.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);

        return bookRepository.findById(isbn).map(foundBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(foundBook::setTitle);
//            Optional.ofNullable(bookEntity.getAuthor()).ifPresent(foundBook::setAuthor);
            return bookRepository.save(foundBook);
        }).orElseThrow(
                () -> new RuntimeException("Book not found")
        );
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
