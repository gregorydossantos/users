package com.gregory.api.users.services.encryption.impl;

import com.gregory.api.users.services.encryption.IEncryptionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor(force = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EncryptionServiceImpl implements IEncryptionService {

    StrongTextEncryptor encryptor;

    @Value("${encryptor.code}")
    String code;

    @Override
    public String encrypt(String str) {
        return encryptor.encrypt(str);
    }

    @Override
    public String decrypt(String str) {
        return encryptor.decrypt(str);
    }

    @Bean
    private StrongTextEncryptor startEncryption() {
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword(code);
        return encryptor;
    }
}