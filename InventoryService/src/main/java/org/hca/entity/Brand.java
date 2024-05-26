package org.hca.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@RequiredArgsConstructor
@Document("brands")
public class Brand extends BaseEntity{
}
