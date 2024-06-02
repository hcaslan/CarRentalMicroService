package org.hca.service;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.hca.domain.CarResponseDto;
import org.hca.domain.enums.Status;
import org.hca.repository.CarRepository;
import org.hca.util.Helper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @RabbitListener(queues = "q.car.save")
    public void save(CarResponseDto car) {
        carRepository.save(car);
    }

    public List<CarResponseDto> fuzzyFindByName(String searchKey) {
        Query fuzzyQueryName = FuzzyQuery.of(m -> m
                .field("category")
                .value(searchKey)
        )._toQuery();

        NativeQuery query = NativeQuery.builder()
                .withQuery(fuzzyQueryName)
                .build();

        SearchHits<CarResponseDto> searchHits = elasticsearchTemplate.search(query, CarResponseDto.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<CarResponseDto> rangeByPrice(Double minPrice, Double maxPrice) {
        Criteria criteria = new Criteria("dailyPrice").between(minPrice, maxPrice);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

        SearchHits<CarResponseDto> searchHits = elasticsearchTemplate.search(criteriaQuery, CarResponseDto.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public Page<CarResponseDto> findAll(int page, int size) {
        Pageable pageable;
        pageable = PageRequest.of(page, size);
        return carRepository.findAll(pageable);
    }

    public Page<CarResponseDto> filter(int page, int size, String category, String gearType, String fuelType, String minDaily, String maxDaily) {
        Pageable pageable = PageRequest.of(page, size);
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        if (category != null) {
            Query fuzzyQueryCategory = FuzzyQuery.of(m -> m
                    .field("category")
                    .value(category)
            )._toQuery();
            boolQueryBuilder.must(fuzzyQueryCategory);
        }

        if (gearType != null) {
            Query fuzzyQueryGearType = FuzzyQuery.of(m -> m
                    .field("gearType")
                    .value(gearType)
            )._toQuery();
            boolQueryBuilder.must(fuzzyQueryGearType);
        }

        if (fuelType != null) {
            Query fuzzyQueryFuelType = FuzzyQuery.of(m -> m
                    .field("fuelType")
                    .value(fuelType)
            )._toQuery();
            boolQueryBuilder.must(fuzzyQueryFuelType);
        }

        if (Helper.isNullOrEmptyOrWhitespace(minDaily) && Helper.isNullOrEmptyOrWhitespace(maxDaily)) {
            Query priceRangeQuery = RangeQuery.of(r -> r
                    .field("dailyPrice")
                    .gte(JsonData.fromJson(minDaily))
                    .lte(JsonData.fromJson(maxDaily))
            )._toQuery();
            boolQueryBuilder.must(priceRangeQuery);
        }

        Query statusQuery = TermQuery.of(t -> t
                .field("status")
                .value(Status.AVAILABLE.name().toLowerCase())
        )._toQuery();
        boolQueryBuilder.must(statusQuery);

        Query isDeletedQuery = TermQuery.of(t -> t
                .field("deleted")
                .value(false)
        )._toQuery();
        boolQueryBuilder.must(isDeletedQuery);

        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQueryBuilder.build()._toQuery())
                .withPageable(pageable)
                .build();
        SearchHits<CarResponseDto> searchHits = elasticsearchTemplate.search(query, CarResponseDto.class);

        List<CarResponseDto> carResponseDtoList = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        long totalHits = searchHits.getTotalHits();

        return new PageImpl<>(carResponseDtoList, pageable, totalHits);
    }



}
