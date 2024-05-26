package org.hca.dto.request;

import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.entity.enums.Status;

public record CarSaveRequest(

        String modelId, int modelYear,
        String plate, double dailyPrice) {
}
