package org.hca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileSaveRequestDto {
    String firstName;
    String lastName;
    String email;
    String authId;
}
