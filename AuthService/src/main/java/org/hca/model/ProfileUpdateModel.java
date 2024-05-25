package org.hca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileUpdateModel {
    private String authId;
    private String firstName;
    private String lastName;
    private String email;
}
