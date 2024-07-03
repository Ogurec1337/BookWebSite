package ru.Harevich.BookSite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Harevich.BookSite.Services.UserService;
import ru.Harevich.BookSite.models.User;

@Component
public class UserValidator implements Validator {
    private final UserService service;

    @Autowired
    public UserValidator(UserService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;
        if(service.getByUsername(user.getUsername()).isPresent())
            errors.rejectValue("username","","Such username already exists");
        if(service.getByEmail(user.getEmail()).isPresent())
            errors.rejectValue("email","","User with such email already exists");
    }
}
