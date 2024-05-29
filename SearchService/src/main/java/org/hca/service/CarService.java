package org.hca.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
import org.hca.domain.Car;
import org.hca.repository.CarRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
//import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Todo: Elastic search did not work as expected fix it!!!
 * Brand = Fiat Model = Egea
 * Search key = Fied OK
 * Search key = Agae OK
 * Search key = Fiat Egea NOT OKEY ????
 */

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;
    @RabbitListener(queues = "q.car.save")
    public void save(Car car) {
        carRepository.save(car);
    }

    public List<Car> fuzzyFindByName(String searchKey) {
//        Query fuzzyQueryModelName = FuzzyQuery.of(m -> m
//                .field("modelName")
//                .value(searchKey)
//        )._toQuery();
//        Query fuzzyQueryBrandName = FuzzyQuery.of(m -> m
//                .field("brandName")
//                .value(searchKey)
//        )._toQuery();
//        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder().should(fuzzyQueryModelName,fuzzyQueryBrandName);
//        NativeQuery query =  NativeQuery.builder()
//                .withQuery(boolQueryBuilder.build()._toQuery())
//                .build();

        Query fuzzyQueryName = FuzzyQuery.of(m -> m
                .field("name")
                .value(searchKey)
        )._toQuery();
        NativeQuery query =  NativeQuery.builder()
                .withQuery(fuzzyQueryName)
                .build();

        // Execute the search query
        SearchHits<Car> searchHits = elasticsearchTemplate.search(query, Car.class);

        // Map the search hits to a list of Car objects
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Car> rangeByPrice(Double minPrice, Double maxPrice) {
        Criteria criteria = new Criteria("dailyPrice").between(minPrice, maxPrice);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

        SearchHits<Car> searchHits = elasticsearchTemplate.search(criteriaQuery, Car.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
