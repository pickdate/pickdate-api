package com.pickdate.iam;

import com.pickdate.shared.model.Encryptor;


class NoopEncryptor implements Encryptor {

    @Override
    public String encrypt(String value) {
        return value;
    }

    @Override
    public String decrypt(String value) {
        return value;
    }
}
