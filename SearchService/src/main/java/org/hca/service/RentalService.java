package org.hca.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.hca.domain.CarResponseDto;
import org.hca.domain.Rental;
import org.hca.repository.RentalRepository;
import org.hca.util.Helper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final RentalRepository rentalRepository;

    @RabbitListener(queues = "q.rental.save")
    public void save(Rental rental) {
        rentalRepository.save(rental);
    }

    public Page<Rental> findRentalsInATimeInterval(String startDate, String endDate) {
        Pageable pageable = PageRequest.of(0, 1000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateLocalDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateLocalDateTime = LocalDateTime.parse(endDate, formatter);
        long startDateEpoch = startDateLocalDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
        long endDateEpoch = endDateLocalDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;


        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        Query dateRangeQuery = RangeQuery.of(r -> r
                .field("startDate")
                .gte(JsonData.of(startDateEpoch))
                .lte(JsonData.of(endDateEpoch))
        )._toQuery();
        boolQueryBuilder.mustNot(dateRangeQuery);


        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQueryBuilder.build()._toQuery())
                .withPageable(pageable)
                .build();
        SearchHits<Rental> searchHits = elasticsearchTemplate.search(query, Rental.class);

        List<Rental> rentalList = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        long totalHits = searchHits.getTotalHits();

        return new PageImpl<>(rentalList, pageable, totalHits);
    }

    public Page<Rental> findAll() {
        Pageable pageable = PageRequest.of(0, 1000);
        return rentalRepository.findAll(pageable);
    }

    public List<String> findRentedCars(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateLocalDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateLocalDateTime = LocalDateTime.parse(endDate, formatter);
        long startDateEpoch = startDateLocalDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
        long endDateEpoch = endDateLocalDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
        return rentalRepository.findAllByStartDateBetween(startDateEpoch, endDateEpoch).stream().map(Rental::getCarId).toList();
    }
}
