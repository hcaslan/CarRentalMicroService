package org.hca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {
    @GetMapping("/auth")
    public ResponseEntity<String> getFallbackAuth(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Auth Service Is Not Responding");
    }
    @GetMapping("/profile")
    public ResponseEntity<String> getFallbackProfile(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile Service Is Not Responding");
    }
    @GetMapping("/mail")
    public ResponseEntity<String> getFallbackMail(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mail Service Is Not Responding");
    }
    @GetMapping("/inventory")
    public ResponseEntity<String> getFallbackInventory(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inventory Service Is Not Responding");
    }
}
