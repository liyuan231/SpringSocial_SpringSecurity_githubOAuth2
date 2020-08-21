package com.liyuan.utils.jwt;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

public class KeyPairFactory {
    private KeyStore store;
    private final Object lock = new Object();

    KeyPair create(String keyPath, String keyAlias, String keyPass) {
        ClassPathResource resource = new ClassPathResource(keyPath);
        char[] pem = keyPass.toCharArray();
        try {
            synchronized (lock) {
                if (store == null) {
                    synchronized (lock) {
                        store = KeyStore.getInstance("jks");
                        store.load(resource.getInputStream(), pem);
                    }
                }
            }
            RSAPrivateCrtKey privateCrtKey = (RSAPrivateCrtKey) store.getKey(keyAlias, pem);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(privateCrtKey.getModulus(), privateCrtKey.getPublicExponent());
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
            return new KeyPair(publicKey, privateCrtKey);
        } catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | InvalidKeySpecException e) {
            throw new IllegalStateException("can not load keys from store: " + resource, e);
        }
    }
}
