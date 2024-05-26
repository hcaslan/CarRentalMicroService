package org.hca.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Document("models")
public class Model extends BaseEntity{
    private String brandId;
}