package org.hca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank
        String token,
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        @Size(min = 8)
        String newPassword,
        @NotBlank
        @Size(min = 8)
        String newPasswordConfirm) {
}
