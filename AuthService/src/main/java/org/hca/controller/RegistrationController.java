package org.hca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.dto.request.RegistrationRequest;
import org.hca.service.AuthService;
import org.hca.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.hca.constant.Constants.*;
import static org.hca.constant.EndPoints.*;


@RestController
@RequestMapping(ROOT+AUTH)
@RequiredArgsConstructor
@CrossOrigin
public class RegistrationController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid registration data.");
        }
        authService.register(request);
        return ResponseEntity.ok("Registration successful.");
    }

    @PostMapping(CONFIRM)
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
        System.out.println("Confirmation Started");
        String result = tokenService.confirmEmailToken(token);
        System.out.println(token);
        if (result.equals("valid")) {
            return ResponseEntity.status(HttpStatus.OK).body("Registration");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration");
        }
    }
}
