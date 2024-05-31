package org.hca.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hca.domain.enums.Category;
import org.hca.domain.enums.FuelType;
import org.hca.domain.enums.GearType;
import org.hca.domain.enums.Status;
import org.springframework.data.elasticsearch.annotations.Document;



@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(indexName = "cars")
public class CarResponseDto {
    private String id;
    private String name;
    private String brand;
    private String model;
    private Category category;
    private FuelType fuelType;
    private GearType gearType;
    private Status status;
    private String image;
    private String plate;
    private  int modelYear;
    private double dailyPrice;
    private int seats;
}
