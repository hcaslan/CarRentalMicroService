package org.hca.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hca.domain.enums.Category;
import org.hca.domain.enums.FuelType;
import org.hca.domain.enums.GearType;
import org.hca.domain.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(indexName = "cars")
public class Car {
    @Id
    private String id;
    private String modelName;
    private String brandName;
    private Category category;
    private FuelType fuelType;
    private GearType gearType;
    private Status status;
    private boolean deleted;
    private  int modelYear;
    private String plate;
    private double dailyPrice;
}