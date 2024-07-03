package ru.Harevich.BookSite.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Harevich.BookSite.models.User;
import ru.Harevich.BookSite.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registrate(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public Optional<List<User>> getByNameStartingWith(String startingWith) {
        return userRepository.findByUsernameStartingWith(startingWith);
    }

    public void delete(int id) {
        userRepository.delete(userRepository.findById(id).get());
    }
}
