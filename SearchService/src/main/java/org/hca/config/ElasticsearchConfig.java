package org.hca.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hca.util.LocalDateTimeToLongConverter;
import org.hca.util.LongToLocalDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.util.Arrays;

@Configuration
public class ElasticsearchConfig {
    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(
                new LongToLocalDateTimeConverter(),
                new LocalDateTimeToLongConverter()
        ));
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        return mapper;
//    }
}
