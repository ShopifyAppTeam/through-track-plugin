package com.appteam.template.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class AuthController {
    @GetMapping("/google_auth")
    public String greetings() { // http://localhost:8080/oauth2/authorization/google
        return "Greetings from Application!\n";
    }
}
