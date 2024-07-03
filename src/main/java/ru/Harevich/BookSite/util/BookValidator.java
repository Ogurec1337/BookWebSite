package ru.Harevich.BookSite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Harevich.BookSite.Services.BookService;
import ru.Harevich.BookSite.models.Book;
import ru.Harevich.BookSite.models.User;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book)target;
        if(bookService.getByName(book.getName()).isPresent())
            errors.rejectValue("name","","Such book already exists");

    }
}
