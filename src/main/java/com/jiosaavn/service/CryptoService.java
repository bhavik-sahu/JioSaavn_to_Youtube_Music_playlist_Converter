package com.jiosaavn.service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoService {

    // Built once, reused for every decrypt() call instead of rebuilding per song.
    private static final SecretKeySpec KEY = new SecretKeySpec("38346591".getBytes(), 0, 8, "DES");

    public static String decrypt(String enc) {
        try {
            Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, KEY);
            byte[] data = Base64.getDecoder().decode(enc.trim());
            return new String(c.doFinal(data)).replace("_96.mp4", "_320.mp4");
        } catch (Exception e) {
            return null;
        }
    }
}