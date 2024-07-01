package org.hca.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Office pickupOffice;
    private Office dropoffOffice;
    private boolean deleted;
}