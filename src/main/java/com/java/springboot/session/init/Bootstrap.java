package com.java.springboot.session.init;

import com.java.springboot.session.entity.User;
import com.java.springboot.session.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Bootstrap {

    private final UserRepository userRepository;

    @PostConstruct
    public void saveUsers() {
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                    new User("1", "Alex", "alex11@gmail.com", "alex11", "USER"),
                    new User("2", "John", "john11@gmail.com", "john11", "ADMIN")
            );
            userRepository.saveAll(users);
        }
    }
}
