package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.entity.enums.AppUserRole;
import org.hca.dto.request.ChangePasswordRequest;
import org.hca.dto.request.CreatePasswordRequest;
import org.hca.entity.AppUser;
import org.hca.service.AppUserService;
import org.hca.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT+AUTH)
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    private final AuthService authService;
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
    @DeleteMapping(DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String appUserId) {
        return ResponseEntity.ok(appUserService.softDeleteAppUser(appUserId));
    }
    @GetMapping(FIND_ALL)
    @PreAuthorize("hasAnyAuthority(T(org.hca.entity.enums.AppUserRole).ADMIN.name())")
    public List<AppUser> getAllAppUsersByRedis() {
        return appUserService.getAllCache();
    }

    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public AppUser findById(@PathVariable("id") String id) {
        return appUserService.findById(id);
    }
}
