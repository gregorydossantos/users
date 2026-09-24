package com.gregory.api.users.services.encryption.impl;

import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class EncryptionServiceImplTest {

    @Mock
    StrongTextEncryptor encryptor;

    @InjectMocks
    EncryptionServiceImpl encryptionService;

    @Test
    @DisplayName("SERVICE LAYER ::: Encrypt password field from user with successfully")
    void encrypt() {
        when(encryptor.encrypt(anyString())).thenReturn(anyString());

        var response = encryptionService.encrypt("test");
        assertNotNull(response);
    }

    @Test
    @DisplayName("SERVICE LAYER ::: Decrypt password field from user with successfully")
    void decrypt() {
        when(encryptor.decrypt(anyString())).thenReturn(anyString());

        var response = encryptionService.decrypt("test");
        assertNotNull(response);
    }
}