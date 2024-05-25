package org.hca.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "tokens")
public class Token implements Serializable {
    @MongoId
    private String id;
    private String token;
    private String appUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiresAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime confirmedAt;


    public Token(String token, String appUserId, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUserId = appUserId;
    }
}
