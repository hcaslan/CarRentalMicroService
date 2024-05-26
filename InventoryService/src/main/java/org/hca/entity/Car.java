package org.hca.entity;

import lombok.*;
import org.hca.entity.enums.Category;
import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.entity.enums.Status;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document("cars")
public class Car {
    @MongoId
    private String id;
    private String modelId;
    private Category category;
    private FuelType fuelType;
    private GearType gearType;
    @Builder.Default
    private Status status = Status.AVAILABLE;
    private boolean deleted;
    private  int modelYear;
    private String plate;
    private double dailyPrice;
}