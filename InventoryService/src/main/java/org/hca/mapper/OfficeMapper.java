package org.hca.mapper;

import org.hca.dto.request.OfficeSaveRequest;
import org.hca.dto.request.OfficeUpdateRequest;
import org.hca.entity.Office;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OfficeMapper {
    OfficeMapper INSTANCE = Mappers.getMapper(OfficeMapper.class);
    Office dtoToOffice(OfficeSaveRequest dto);
    Office dtoToOffice(OfficeUpdateRequest dto);
}
