package com.admin.modules.developer.web;

import com.admin.modules.developer.application.dto.DeveloperProfileDto;
import com.admin.modules.developer.application.service.DeveloperProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developer/profile")
@RequiredArgsConstructor
public class DeveloperProfileController {

    private final DeveloperProfileService profileService;

    @GetMapping
    public ResponseEntity<DeveloperProfileDto> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(profileService.getMyProfile(authentication.getName()));
    }

    @PutMapping
    public ResponseEntity<DeveloperProfileDto> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody DeveloperProfileDto request
    ) {
        return ResponseEntity.ok(profileService.updateMyProfile(authentication.getName(), request));
    }
}
