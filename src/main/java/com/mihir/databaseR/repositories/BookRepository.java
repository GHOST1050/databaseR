package com.mihir.databaseR.repositories;

import com.mihir.databaseR.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> , PagingAndSortingRepository<BookEntity, String> {
}
