package org.hca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingsController {
    @GetMapping("/user")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from our api");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> sayGoodBye() {
        return ResponseEntity.ok("Greetings to the Admin");
    }
}
