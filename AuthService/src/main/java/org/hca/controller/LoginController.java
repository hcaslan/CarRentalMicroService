package org.hca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // This refers to login.html in src/main/resources/templates
    }
    @GetMapping("/welcome")
    public String showWelcome() {
        return "welcome"; // This refers to welcome.html in src/main/resources/templates
    }

}
