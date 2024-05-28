package org.hca.mapper;

import org.hca.domain.Car;
import org.hca.dto.CarRabbitMqDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    Car dtoToCar(CarRabbitMqDto dto);
}
