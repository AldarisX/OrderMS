package com.everygamer.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class RSAUtils {
    public final static String RSAMODE = "RSA/None/PKCS1Padding";
    public final static String RSATYPE = "BC";
    public static PublicKey pubKey;
    public static PrivateKey priKey;
    private static KeyPair keyPair;

    private static KeyPair initKey() {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024, random);
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
        return new String(decrypt(Base64.decodeBase64(string)));
    }

    /**
     * 解密
     *
     * @param string
     * @return
     */
    public static String decryptBase64(PrivateKey priKey, String string) {
        try {
            return new String(decrypt(priKey, Base64.decodeBase64(string)));
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(PrivateKey pirKey, byte[] string) throws NoSuchProviderException {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(RSAMODE, RSATYPE);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] plainText = cipher.doFinal(string);
            return plainText;
        } catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
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
        return new String(encrypt(Base64.decodeBase64(string)));
    }

    private static byte[] decrypt(byte[] string) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(RSAMODE, RSATYPE);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] plainText = cipher.doFinal(string);
            return plainText;
        } catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | NoSuchPaddingException | NoSuchProviderException | IllegalBlockSizeException e) {
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

    public static String getPubKey() {
        return new String(Base64.encodeBase64(pubKey.getEncoded()));
    }

    public static String getPriKey() {
        return new String(Base64.encodeBase64(priKey.getEncoded()));
    }
}