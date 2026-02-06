package com.pickdate.iam;

import com.pickdate.shared.Try;

import javax.crypto.KeyGenerator;
import java.security.SecureRandom;
import java.util.Base64;


class AESKeyGenerator {

    private static final String ALGORITHM = "AES";

    static String generateKey() {
        KeyGenerator gen = Try.of(() -> KeyGenerator.getInstance(ALGORITHM));
        gen.init(256, new SecureRandom());
        var secret = gen.generateKey();
        var bytes = secret.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
