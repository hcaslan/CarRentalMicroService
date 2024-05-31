package org.hca.dto.response;

import lombok.*;
import org.hca.entity.enums.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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
