package ru.Harevich.BookSite.detail_services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.Harevich.BookSite.models.User;
import ru.Harevich.BookSite.repositories.UserRepository;
import ru.Harevich.BookSite.security.MyUserDetails;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            throw new UsernameNotFoundException("username with such name doesn't exist");

        return new MyUserDetails(user.get());

    }
}
