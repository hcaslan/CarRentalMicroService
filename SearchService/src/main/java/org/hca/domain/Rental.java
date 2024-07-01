package org.hca.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Office pickupOffice;
    private Office dropoffOffice;
    private boolean deleted;
}