package org.hca.dto.request;

public record CreatePasswordRequest(
        String token,
        String email,
        String password,
        String passwordConfirm) {
}
