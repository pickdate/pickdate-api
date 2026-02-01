package com.pickdate.shared.model;

public interface Encryptor {

    String encrypt(String value);

    String decrypt(String value);
}
