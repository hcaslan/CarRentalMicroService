package org.hca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileSaveModel {
    String firstName;
    String lastName;
    String email;
    String authId;
}
