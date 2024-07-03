package ru.Harevich.BookSite.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Harevich.BookSite.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findByUrl(String url);
    Page<Book> findByNameStartingWith(String startingWith, PageRequest pageRequest);

    Optional<Object> findByName(String name);

    Optional<List<Book>> findByNameStartingWith(String pattern);
}
