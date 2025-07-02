package com.gregory.api.users.services.encryption;

public interface IEncryptionService {

    String encrypt(String str);

    String decrypt(String str);
}