package org.hca.dto.request;

import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.entity.enums.Status;

public record CarUpdateRequest(

        String id, String modelId, int modelYear,String image,
        String plate, double dailyPrice) {
}
