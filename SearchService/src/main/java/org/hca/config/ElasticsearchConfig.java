package org.hca.config;

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
}
