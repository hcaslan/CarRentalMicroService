package org.hca.mapper;

import org.hca.dto.request.BrandSaveRequest;
import org.hca.dto.request.BrandUpdateRequest;
import org.hca.dto.request.ModelSaveRequest;
import org.hca.dto.request.ModelUpdateRequest;
import org.hca.entity.Brand;
import org.hca.entity.Model;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);
    Model dtoToModel(ModelSaveRequest dto);
    Model dtoToModel(ModelUpdateRequest dto);
}
