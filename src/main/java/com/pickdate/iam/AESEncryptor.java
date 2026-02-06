package com.pickdate.iam;

import com.pickdate.shared.Try;
import com.pickdate.shared.encryption.Encryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;


class AESEncryptor implements Encryptor {

    private static final String ALGORITHM = "AES";

    private final Key key;
    private final ThreadLocal<Cipher> cipher;

    private AESEncryptor(String key) {
        byte[] bytes = Base64.getDecoder().decode(key);
        this.key = new SecretKeySpec(bytes, ALGORITHM);
        this.cipher = ThreadLocal.withInitial(() -> Try.of(() -> Cipher.getInstance(ALGORITHM)));
    }

    public static AESEncryptor create(String key) {
        return new AESEncryptor(key);
    }

    @Override
    public String encrypt(String value) {
        if (value == null) return null;
        setCipherMode(ENCRYPT_MODE);
        var bytes = encodeToBytes(value);
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public String decrypt(String value) {
        if (value == null) return null;
        setCipherMode(DECRYPT_MODE);
        var bytes = decodeToBytes(value);
        return new String(bytes, UTF_8);
    }

    private void setCipherMode(int encryptMode) {
        Cipher cipher = this.cipher.get();
        Try.run(() -> cipher.init(encryptMode, key));
    }

    private byte[] encodeToBytes(String value) {
        Cipher cipher = this.cipher.get();
        return Try.of(() -> cipher.doFinal(value.getBytes(UTF_8)));
    }

    private byte[] decodeToBytes(String value) {
        Cipher cipher = this.cipher.get();
        return Try.of(() -> cipher.doFinal(Base64.getDecoder().decode(value)));
    }
}
