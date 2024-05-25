package org.hca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileUpdateRequestDto {
    String token;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
