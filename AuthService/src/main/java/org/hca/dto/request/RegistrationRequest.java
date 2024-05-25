package org.hca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegistrationRequest {
    @NotNull
    @Size(min = 2, max = 32)
    private String firstname;
    @NotNull
    @Size(min = 2, max = 32)
    private String lastname;
    @NotNull
    @Size(min = 8)
    private String password;
    @NotNull
    @Email
    private String email;
}
