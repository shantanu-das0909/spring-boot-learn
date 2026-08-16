package com.ecom.authservice.service;

import com.ecom.authservice.model.Role;
import com.ecom.authservice.model.User;
import com.ecom.authservice.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthUserInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserDetailsRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                User newUser = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ROLE_ADMIN).build();

                userRepository.save(newUser);
            }
        };
    }
}
