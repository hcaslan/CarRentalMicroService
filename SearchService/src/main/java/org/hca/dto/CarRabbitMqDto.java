package org.hca.dto;

import org.hca.domain.enums.Category;
import org.hca.domain.enums.FuelType;
import org.hca.domain.enums.GearType;
import org.hca.domain.enums.Status;

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
