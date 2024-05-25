package org.hca.mapper;

import org.hca.dto.request.RegistrationRequest;
import org.hca.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppUser registrationRequestToAppUser(RegistrationRequest registrationRequest);
}