package org.hca.dto.request;

import lombok.Builder;
import org.hca.entity.Office;

import java.time.LocalDate;
@Builder
public record RentalSaveRequest(
        String token,
        String carId,
        String startDate,
        String endDate,
        String pickupOffice,
        String dropoffOffice) {
}
