package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.ProfileSaveRequestDto;
import org.hca.dto.request.ProfileUpdateRequestDto;
import org.hca.entity.Profile;
import org.hca.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Profile>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Profile> findById(@PathVariable String id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(SAVE)
    public ResponseEntity<Profile> save(@RequestBody ProfileSaveRequestDto dto){
        return ResponseEntity.ok(service.saveDto(dto));
    }

}
