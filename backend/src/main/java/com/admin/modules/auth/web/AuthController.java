package com.admin.modules.auth.web;

import com.admin.modules.auth.application.dto.*;
import com.admin.modules.auth.domain.InviteToken;
import com.admin.modules.auth.application.service.AuthService;
import com.admin.modules.user.application.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(authService.me(email));
    }

    @GetMapping("/accept-invite")
    public ResponseEntity<Map<String, String>> getInviteEmail(@RequestParam String token) {
        InviteToken inviteToken = authService.getInviteToken(token);

        Map<String, String> response = Map.of(
                "email", inviteToken.getUser().getEmail(),
                "name", inviteToken.getUser().getName()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accept-invite")
    public ResponseEntity<String> acceptInvite(
            @RequestParam String token,
            @RequestParam String password
    ) {
        authService.acceptInvite(token, password);
        return ResponseEntity.ok("Account activated successfully");
    }
}
