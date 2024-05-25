package org.hca.mapper;

import org.hca.model.ProfileSaveModel;
import org.hca.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    ProfileMapper INSTANCE= Mappers.getMapper(ProfileMapper.class);

    Profile dtoToProfile(ProfileSaveModel dto);

}