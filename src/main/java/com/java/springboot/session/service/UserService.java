package com.java.springboot.session.service;

import com.java.springboot.session.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String login(String email, String password, HttpServletRequest request) {
        return userRepository.findByEmailIgnoreCase(email).map(user -> {
            if(!user.getPassword().equals(password)) {
                return "Invalid Credentials";
            }

            HttpSession session = request.getSession(true);

            session.setAttribute("email" ,user.getEmail());
            session.setAttribute("role" ,user.getRole());
            session.setAttribute("userId" ,user.getId());

            return "Login successful";
        }).orElse("Invalid Credentials");
    }

    public String profile(HttpSession session) {
        if (session == null) {
            return "Not LoggedIn";
        }
        Object email = session.getAttribute("email");
        Object role = session.getAttribute("role");

        return "Welcome : " + email + " With Role : " + role;
    }
}
