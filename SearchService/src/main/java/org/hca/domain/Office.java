package org.hca.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hca.domain.enums.OfficeType;
import org.springframework.data.elasticsearch.annotations.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@RequiredArgsConstructor
@Document(indexName = "offices")
public class Office extends BaseEntity{
    private String city;
    private OfficeType officeType;
}
