package com.everygamer.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;
import java.util.Objects;

public class RSAUtils {
    private final static String RSAMODE = "RSA/None/PKCS1Padding";
    private final static String RSATYPE = "BC";
    private static PublicKey pubKey;
    private static PrivateKey priKey;
    private static KeyPair keyPair;

    private static KeyPair initKey() {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(2048, random);
            return generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成RSA
     */
    public static void generateRSA() {
        keyPair = initKey();
        pubKey = keyPair.getPublic();
        priKey = keyPair.getPrivate();
    }

    /**
     * 解密
     *
     * @param string
     * @return
     */
    public static String decryptBase64(String string) {
        return new String(Objects.requireNonNull(decrypt(Base64.getDecoder().decode(string))));
    }

    /**
     * 解密
     *
     * @param string
     * @return
     */
    public static String decryptBase64(PrivateKey priKey, String string) {
        try {
            return new String(Objects.requireNonNull(decrypt(priKey, Base64.getDecoder().decode(string))));
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param string
     * @return
     */
    public static String encryptBase64(String string) {
        return new String(Objects.requireNonNull(encrypt(Base64.getDecoder().decode(string))));
    }

    private static byte[] decrypt(byte[] string) {
        try {
            return decrypt(priKey, string);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(PrivateKey priKey, byte[] string) throws NoSuchProviderException {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(RSAMODE, RSATYPE);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return cipher.doFinal(string);
        } catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(byte[] plainText) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(RSAMODE, RSATYPE);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPubKeyString() {
        return new String(Base64.getEncoder().encode(pubKey.getEncoded()));
    }

    public static String getPriKeyString() {
        return new String(Base64.getEncoder().encode(priKey.getEncoded()));
    }

    public static PublicKey getPubKey() {
        return pubKey;
    }

    public static PrivateKey getPriKey() {
        return priKey;
    }
}