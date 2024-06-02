package org.hca.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import lombok.RequiredArgsConstructor;
import org.hca.domain.CarResponseDto;
import org.hca.domain.Office;
import org.hca.repository.OfficeRepository;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficeService {
    private final OfficeRepository officeRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @RabbitListener(queues = "q.office.save")
    public void save(Office office) {
        officeRepository.save(office);
    }
    public Page<Office> filter(int page, int size, String city, String officeType) {
        Pageable pageable = PageRequest.of(page, size);
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        if (city != null) {
            Query fuzzyQueryCity = TermQuery.of(m -> m
                    .field("city")
                    .value(city)
            )._toQuery();
            boolQueryBuilder.must(fuzzyQueryCity);
        }
        if (officeType != null) {
            Query fuzzyQueryOfficeType = TermQuery.of(m -> m
                    .field("officeType")
                    .value(officeType)
            )._toQuery();
            boolQueryBuilder.must(fuzzyQueryOfficeType);
        }
        Query isDeletedQuery = TermQuery.of(t -> t
                .field("deleted")
                .value(false)
        )._toQuery();
        boolQueryBuilder.must(isDeletedQuery);

        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQueryBuilder.build()._toQuery())
                .withPageable(pageable)
                .build();
        SearchHits<Office> searchHits = elasticsearchTemplate.search(query, Office.class);

        List<Office> officeList = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        long totalHits = searchHits.getTotalHits();

        return new PageImpl<>(officeList, pageable, totalHits);
    }

    public Page<Office> findAll() {
        Pageable pageable = PageRequest.of(0, 1000);
        return officeRepository.findAll(pageable);
    }
}
