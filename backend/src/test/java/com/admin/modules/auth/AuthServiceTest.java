package com.admin.modules.auth;

import com.admin.modules.auth.application.dto.AuthResponse;
import com.admin.modules.auth.application.dto.LoginRequest;
import com.admin.modules.auth.application.service.AuthService;
import com.admin.modules.auth.application.service.JwtService;
import com.admin.modules.auth.infrastructure.InviteTokenRepository;
import com.admin.modules.user.domain.Role;
import com.admin.modules.user.domain.User;
import com.admin.modules.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private InviteTokenRepository inviteTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("Admin User")
                .email("admin@nexora.com")
                .role(Role.ADMIN)
                .enabled(true)
                .password("encoded_pass")
                .build();
    }

    @Test
    @DisplayName("Should successfully authenticate user and return JWT token")
    void testLoginSuccess() {
        LoginRequest req = new LoginRequest();
        req.setEmail("admin@nexora.com");
        req.setPassword("admin123");

        when(userRepository.findByEmail("admin@nexora.com")).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn("mocked_jwt_token");

        AuthResponse response = authService.login(req);

        assertNotNull(response);
        assertEquals("mocked_jwt_token", response.getToken());
        assertEquals("admin@nexora.com", response.getUser().getEmail());
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    @DisplayName("Should throw exception when authentication fails")
    void testLoginInvalidCredentials() {
        LoginRequest req = new LoginRequest();
        req.setEmail("wrong@nexora.com");
        req.setPassword("wrongpass");

        doThrow(new BadCredentialsException("Invalid email or password"))
                .when(authenticationManager).authenticate(any());

        assertThrows(BadCredentialsException.class, () -> authService.login(req));
    }
}
