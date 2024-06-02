package org.hca.repository;

import org.hca.domain.Office;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends ElasticsearchRepository<Office,String> {

}
