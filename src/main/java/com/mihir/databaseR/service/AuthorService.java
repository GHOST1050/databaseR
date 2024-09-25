package com.mihir.databaseR.service;

import com.mihir.databaseR.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;


public interface AuthorService {

    AuthorEntity saveAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(long id);

    boolean ifDoesNotExists(long id);

    AuthorEntity partialUpdate(long id, AuthorEntity author);

    void delete(long id);
}
