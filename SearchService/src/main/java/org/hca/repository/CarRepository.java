package org.hca.repository;

import org.hca.domain.CarResponseDto;
import org.hca.domain.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends ElasticsearchRepository<CarResponseDto,String> {
    Page<CarResponseDto> findAllByCategory(Category category, Pageable pageable);
}
