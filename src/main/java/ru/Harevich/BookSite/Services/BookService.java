package ru.Harevich.BookSite.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.Harevich.BookSite.models.Book;
import ru.Harevich.BookSite.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final int booksPerPage = 16;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(int page,String sort){
        return bookRepository.findAll(PageRequest.of
                (page-1,booksPerPage, Sort.by(sort)))
                .getContent();
    }
    public Book getByUrl(String url){
        return bookRepository.findByUrl(url);
    }

    public List<Book> searchForBooks(int page, String sort,String pattern) {
        return bookRepository.findByNameStartingWith(pattern,PageRequest.of
                        (page-1,booksPerPage, Sort.by(sort)))
                        .getContent();
    }


    public Optional<Object> getByName(String name) {
        return bookRepository.findByName(name);
    }

    public void create(Book book) {
        bookRepository.save(book);
    }

    public Optional<List<Book>> getByNameStartingWith(String pattern) {
        return bookRepository.findByNameStartingWith(pattern);
    }

    public void delete(int id) {
        bookRepository.delete(bookRepository.findById(id).get());
    }
}
