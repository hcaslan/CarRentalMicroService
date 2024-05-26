package org.hca.mapper;

import org.hca.dto.request.CarSaveRequest;
import org.hca.dto.request.CarUpdateRequest;
import org.hca.dto.request.ModelSaveRequest;
import org.hca.dto.request.ModelUpdateRequest;
import org.hca.entity.Car;
import org.hca.entity.Model;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    Car dtoToCar(CarSaveRequest dto);
    Car dtoToCar(CarUpdateRequest dto);
}
