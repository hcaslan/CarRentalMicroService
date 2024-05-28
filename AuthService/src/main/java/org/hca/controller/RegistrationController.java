package org.hca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.dto.request.RegistrationRequest;
import org.hca.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final AuthService authService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registration", new RegistrationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("registration") RegistrationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        authService.register(request);
        return "redirect:/login";
    }
}