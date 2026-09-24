package com.gregory.api.users.domain.mapper;

import com.gregory.api.users.infra.db.entities.UserEntity;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;

import static com.gregory.api.users.infra.db.enums.ExchangeType.getDescriptionByCode;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface IUserMapper {
    @Mapping(source = "exchangeCode", target = "exchange", qualifiedByName = "getDescription")
    @Mapping(target = "userId", expression = "java(UUID.randomUUID().toString())")
    UserEntity toEntity(UserRequest request);

    UserResponse toResponse(UserEntity user);

    List<UserResponse> toListResponse(List<UserEntity> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(source = "exchangeCode", target = "exchange", qualifiedByName = "getDescription")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity toUpdate(@MappingTarget UserEntity user, UserUpdateRequest request);

    @Named("getDescription")
    static String getDescription(int code) {
        return getDescriptionByCode(code);
    }
}