package com.example.Lab5.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lab5/test")
public class HomeController {

    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public String hello() {
        return "Hello from protected endpoint!";
    }

    @GetMapping("/name")
    @PreAuthorize("isAuthenticated()")
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello from protected endpoint to user " + authentication.getName();
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

}