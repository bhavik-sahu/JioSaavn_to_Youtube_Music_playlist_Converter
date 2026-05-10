package com.jiosaavn.service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoService {
    public static String decrypt(String enc) {
        try {
            byte[] data = Base64.getDecoder().decode(enc.trim());
            Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec("38346591".getBytes(), 0, 8, "DES"));
            return new String(c.doFinal(data)).replace("_96.mp4", "_320.mp4");
        } catch (Exception e) {
            return null;
        }
    }
}
