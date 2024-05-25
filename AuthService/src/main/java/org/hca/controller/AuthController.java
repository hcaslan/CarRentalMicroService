package org.hca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.dto.request.AuthenticationRequest;
import org.hca.dto.request.ChangePasswordRequest;
import org.hca.dto.request.CreatePasswordRequest;
import org.hca.dto.request.RegistrationRequest;
import org.hca.entity.AppUser;
import org.hca.entity.Token;
import org.hca.exception.AuthServiceException;
import org.hca.exception.ErrorType;
import org.hca.service.AppUserService;
import org.hca.service.AuthService;
import org.hca.service.TokenService;
import org.hca.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AppUserService appUserService;
    private final TokenService tokenService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(appUserService.changePassword(request));
    }
    @GetMapping(RESET_PASSWORD)
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email){
        authService.resetPassword(email);
        return ResponseEntity.ok().build();
    }
    @PutMapping(CREATE_PASSWORD)
    public ResponseEntity<String> createPassword(@RequestBody CreatePasswordRequest request){
        return ResponseEntity.ok(authService.createPassword(request));
    }

    @PostMapping(AUTHENTICATION)
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (AuthenticationException e) {
            throw new AuthServiceException(ErrorType.INCORRECT_EMAIL_OR_PASSWORD);
        }
        final UserDetails user = appUserService.loadUserByUsername(request.email());
        if (user != null) {
            return ResponseEntity.ok(jwtUtil.generateToken(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(REGISTRATION)
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping(CONFIRMATION)
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(tokenService.confirmEmailToken(token));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String appUserId) {
        return ResponseEntity.ok(appUserService.softDeleteAppUser(appUserId));
    }
    @GetMapping("/findAll")
    public List<AppUser> getAllAppUsersByRedis() {
        return appUserService.getAllCache();
    }

    @GetMapping("/{id}")
    public AppUser findById(@PathVariable("id") String id) {
        return appUserService.findById(id);
    }
    @GetMapping("/token/findAll")
    public List<Token> getAllTokensByRedis() {
        return tokenService.getAllCache();
    }
}
