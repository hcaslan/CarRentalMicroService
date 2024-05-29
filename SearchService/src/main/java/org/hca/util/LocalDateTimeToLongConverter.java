package org.hca.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@WritingConverter
public class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {
    @Override
    public Long convert(LocalDateTime source) {
        return source.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}