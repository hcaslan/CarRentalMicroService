package org.hca.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hca.dto.request.AuthenticationRequest;
import org.hca.exception.AuthServiceException;
import org.hca.exception.ErrorType;
import org.hca.service.AppUserService;
import org.hca.service.AuthService;
import org.hca.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.hca.constant.Constants.LOGIN_HTML;
import static org.hca.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT+AUTH)
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserService appUserService;
    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest loginRequest, HttpSession session, Principal principal) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        } catch (AuthenticationException e) {
            throw new AuthServiceException(ErrorType.INCORRECT_EMAIL_OR_PASSWORD);
        }
        final UserDetails user = appUserService.loadUserByUsername(loginRequest.email());
        if (user != null) {
            return ResponseEntity.ok(jwtUtil.generateToken(user));
        }
        return ResponseEntity.badRequest().build();
    }
}

