package com.admin.modules.auth.application.dto;

import com.admin.modules.user.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InviteRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private Role role;
}
