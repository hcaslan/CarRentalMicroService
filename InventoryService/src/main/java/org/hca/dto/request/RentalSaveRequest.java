package org.hca.dto.request;

import org.hca.entity.Office;

import java.time.LocalDate;

public record RentalSaveRequest(
        String profileId,
        String carId,
        LocalDate startDate,
        LocalDate endDate,
        Office pickupOffice,
        Office dropoffOffice) {
}
