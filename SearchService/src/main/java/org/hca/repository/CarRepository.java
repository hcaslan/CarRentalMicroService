package org.hca.repository;

import org.hca.domain.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends ElasticsearchRepository<Car,String> {
}
