package org.hca.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document("rentals")
public class Rental {
    @MongoId
    private String id;
    private String profileId;
    private String carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Office pickupOffice;
    private Office dropoffOffice;
    private boolean deleted;
}