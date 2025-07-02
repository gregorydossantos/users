package com.gregory.api.users.domain.mapper;

import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.rest.dto.request.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
class UserMapperTest {

    //@InjectMocks
    @Autowired
    IUserMapper mapper;

    @Test
    @DisplayName("USE CASE LAYER ::: Mapping dto object to entity")
    void should_Return_Entity() {
        var response = mapper.toEntity(mock(UserRequest.class));
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Mapping entity to response object")
    void should_Return_Response() {
        var response = mapper.toResponse(mock(Users.class));
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Mapping entity to response list")
    void should_Return_Response_List() {
        var userMock = mock(Users.class);
        var response = mapper.toListUserResponse(List.of(userMock));
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Mapping dto object to update entity")
    void should_Return_Update_Entity() {
        var response = mapper.toUpdate(mock(Users.class), mock(UserRequest.class));
        assertNotNull(response);
    }
}