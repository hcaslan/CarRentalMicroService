package org.hca.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hca.domain.enums.Category;
import org.hca.domain.enums.FuelType;
import org.hca.domain.enums.GearType;
import org.hca.domain.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(indexName = "cars")
public class Car extends BaseEntity{
    private Category category;
    private FuelType fuelType;
    private GearType gearType;
    private Status status;
    private  int modelYear;
    private String plate;
    private String image;
    private double dailyPrice;
}