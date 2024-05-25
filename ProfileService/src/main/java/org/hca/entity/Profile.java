package org.hca.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile {
    @MongoId
    private String id;
    private String authId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    //Todo: payment methods?
    @Builder.Default
    private EStatus status = EStatus.PENDING;
}
