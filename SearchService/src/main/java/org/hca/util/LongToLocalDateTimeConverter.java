package org.hca.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@ReadingConverter
public class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
    @Override
    public LocalDateTime convert(Long source) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.systemDefault());
    }
}

