package org.hca.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(indexName = "rentals")
public class Rental {
    @Id
    private String id;
    private String profileId;
    private String carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Office pickupOffice;
    private Office dropoffOffice;
    private boolean deleted;
}