package com.mihir.databaseR.service.impl;

import com.mihir.databaseR.domain.entities.AuthorEntity;
import com.mihir.databaseR.repositories.AuthorRepository;
import com.mihir.databaseR.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity saveAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
         return StreamSupport.stream(authorRepository
                        .findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean ifDoesNotExists(long id) {
        return !authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("No author found with id: " + id));
    }

    @Override
    public void delete(long id) {
        authorRepository.deleteById(id);
    }
}
