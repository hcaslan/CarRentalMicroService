package org.hca.repository;

import org.hca.domain.Rental;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends ElasticsearchRepository<Rental,String> {

    @Query("{\"bool\": {\"must\": [" +
            "{\"range\": {\"startDate\": {\"lte\": \"?1\"}}}," +
            "{\"range\": {\"endDate\": {\"gte\": \"?0\"}}}" +
            "]}}")
    List<Rental> findAllByStartDateBetween(Long startDate, Long endDate);

}
