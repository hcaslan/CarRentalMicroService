package org.hca.mapper;

import org.hca.dto.request.BrandSaveRequest;
import org.hca.dto.request.BrandUpdateRequest;
import org.hca.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
    Brand dtoToBrand(BrandSaveRequest dto);
    Brand dtoToBrand(BrandUpdateRequest dto);
}
