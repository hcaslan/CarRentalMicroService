package org.hca.repository;

import org.hca.domain.Rental;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends ElasticsearchRepository<Rental,String> {
}
