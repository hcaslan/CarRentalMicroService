package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.ProfileUpdateRequestDto;
import org.hca.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hca.constant.EndPoints.*;

@RequiredArgsConstructor
@RequestMapping(ROOT+PROFILE)
@RestController
public class ProfileController {
    private final ProfileService service;

    @PutMapping(UPDATE)
    public ResponseEntity<String> update( @RequestBody ProfileUpdateRequestDto dto){
        return ResponseEntity.ok(service.updateProfile(dto));
    }
}
