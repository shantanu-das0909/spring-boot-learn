package com.java.springboot.session.controller;

import com.java.springboot.session.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        String login = userService.login(email, password, request);
        if(login.equalsIgnoreCase("Login successful")) {
            response.setHeader("X-Auth-Token", request.getSession().getId());
        }

        return ResponseEntity.ok(login);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(HttpServletRequest httpServletRequest) {
        String profile = userService.profile(httpServletRequest.getSession(false));
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return ResponseEntity.ok("Logout Successful");
    }

}
