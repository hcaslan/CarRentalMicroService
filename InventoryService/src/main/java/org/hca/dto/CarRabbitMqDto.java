package org.hca.dto;

import org.hca.entity.enums.Category;
import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.entity.enums.Status;

public record CarRabbitMqDto(
        String id,
        String modelName,
        String brandName,
        Category category,
        FuelType fuelType,
        GearType gearType,
        Status status,
        boolean deleted,
        int modelYear,
        String plate,
        double dailyPrice) {
}
