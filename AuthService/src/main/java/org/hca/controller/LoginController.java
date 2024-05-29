package org.hca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hca.constant.Constants.LOGIN_HTML;
import static org.hca.constant.EndPoints.*;

@Controller
public class LoginController {
    @GetMapping(LOGIN)
    public String showLoginForm() {
        return LOGIN_HTML; // This refers to login.html in src/main/resources/templates
    }
    @GetMapping("/welcome")
    public String showWelcome() {
        return "welcome"; // This refers to welcome.html in src/main/resources/templates
    }
}
