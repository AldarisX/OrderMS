package com.everygamer.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Tool {
    public static String StringToMd5(String psw) {
        {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(psw.getBytes("UTF-8"));
                byte[] encryption = md5.digest();

                StringBuilder strBuf = new StringBuilder();
                for (byte anEncryption : encryption) {
                    if (Integer.toHexString(0xff & anEncryption).length() == 1) {
                        strBuf.append("0").append(Integer.toHexString(0xff & anEncryption));
                    } else {
                        strBuf.append(Integer.toHexString(0xff & anEncryption));
                    }
                }

                return strBuf.toString();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                return "";
            }
        }
    }
}
