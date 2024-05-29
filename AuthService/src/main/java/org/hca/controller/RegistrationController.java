package org.hca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.dto.request.RegistrationRequest;
import org.hca.service.AuthService;
import org.hca.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.hca.constant.Constants.*;
import static org.hca.constant.EndPoints.*;


@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final AuthService authService;
    private final TokenService tokenService;

    @GetMapping(REGISTER)
    public String showRegistrationForm(Model model) {
        model.addAttribute(REGISTER_ATTRIBUTE, new RegistrationRequest());
        return REGISTER_HTML;
    }

    @PostMapping(REGISTER)
    public String processRegistration(@Valid @ModelAttribute(REGISTER_ATTRIBUTE) RegistrationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return REGISTER_HTML;
        }
        authService.register(request);
        return REDIRECT+LOGIN;
    }
    @GetMapping(CONFIRM)
    public ModelAndView confirmRegistration(@RequestParam("token") String token) {
        String result = tokenService.confirmEmailToken(token);
        if(result.equals("valid")) {
            return new ModelAndView(REDIRECT+LOGIN);
        } else if(!result.isEmpty()){
            return new ModelAndView("error", "message", result);
        }else{
            return new ModelAndView("error", "message", "Invalid Token");
        }
    }
}