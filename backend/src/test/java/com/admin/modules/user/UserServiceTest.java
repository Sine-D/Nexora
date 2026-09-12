package com.admin.modules.user;

import com.admin.core.dto.PageResponse;
import com.admin.modules.auth.application.service.MailService;
import com.admin.modules.auth.infrastructure.InviteTokenRepository;
import com.admin.modules.user.application.dto.UserResponse;
import com.admin.modules.user.application.service.UserService;
import com.admin.modules.user.domain.Role;
import com.admin.modules.user.domain.User;
import com.admin.modules.user.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private InviteTokenRepository inviteTokenRepository;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should return paginated users list")
    void testGetUsers() {
        User u1 = User.builder().id(1L).name("User One").email("one@nexora.com").role(Role.ADMIN).enabled(true).build();
        User u2 = User.builder().id(2L).name("User Two").email("two@nexora.com").role(Role.DEVELOPER).enabled(true).build();

        when(userRepository.findByFiltersNative(null, null, null, 20, 0)).thenReturn(List.of(u1, u2));
        when(userRepository.countByFilters(null, null, null)).thenReturn(2L);

        PageResponse<UserResponse> page = userService.getUsers(null, null, null, 0, 20);

        assertNotNull(page);
        assertEquals(2, page.getTotal());
        assertEquals(2, page.getItems().size());
        assertEquals("User One", page.getItems().get(0).getName());
    }

    @Test
    @DisplayName("Should update user enabled status")
    void testUpdateUserStatus() {
        User user = User.builder().id(5L).enabled(true).build();
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));

        userService.updateUserStatus(5L, false);

        assertFalse(user.getEnabled());
        verify(userRepository, times(1)).save(user);
    }
}
