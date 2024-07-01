package org.hca.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hca.entity.enums.Category;
import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.entity.enums.Status;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Document("cars")
public class Car extends BaseEntity{
    private String modelId;
    private Category category;
    private FuelType fuelType;
    private GearType gearType;
    @Builder.Default
    private Status status = Status.AVAILABLE;
    private String image;
    private  int modelYear;
    private String plate;
    private double dailyPrice;
    private int seats;
    private List<Rental> rentals = new ArrayList<>();
}