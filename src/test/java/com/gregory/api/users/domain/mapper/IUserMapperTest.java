package com.gregory.api.users.domain.mapper;

import com.gregory.api.users.infra.db.entities.UserEntity;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class IUserMapperTest {

    @Autowired
    IUserMapper mapper;

    @Test
    @DisplayName("MAPPER LAYER ::: Should returns user when call toEntity() method")
    void toEntity() {
        var mockUserRequest = mock(UserRequest.class);
        var user = mapper.toEntity(mockUserRequest);
        assertNotNull(user);
    }

    @Test
    @DisplayName("MAPPER LAYER ::: Should returns user response when call toResponse() method")
    void toResponse() {
        var mockUser = mock(UserEntity.class);
        var userResponse = mapper.toResponse(mockUser);
        assertNotNull(userResponse);
    }

    @Test
    @DisplayName("MAPPER LAYER ::: Should returns user response list when call toListResponse() response method")
    void toListResponse() {
        var mockUser = mock(UserEntity.class);
        var userResponses = mapper.toListResponse(List.of(mockUser));
        assertNotNull(userResponses);
    }

    @Test
    @DisplayName("MAPPER LAYER ::: Should returns user update when call toUpdate() method")
    void toUpdate() {
        var mockUserUpdateRequest = mock(UserUpdateRequest.class);
        var mockUser = Mockito.mock(UserEntity.class);
        var userUpdate = mapper.toUpdate(mockUser, mockUserUpdateRequest);
        assertNotNull(userUpdate);
    }
}