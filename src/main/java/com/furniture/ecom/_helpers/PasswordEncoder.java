/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author HP
 */
public class PasswordEncoder {
    
    private static final String KEY = "Fur##ecom?AA^KSA";
    private static final String VECTOR = "A#@!M$$A$O%H^S&M";
    
    public static String encrypt(String plain) {
        try {
            IvParameterSpec param = new IvParameterSpec(VECTOR.getBytes("UTF-8"));
            SecretKeySpec secKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
            Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            ciper.init(Cipher.ENCRYPT_MODE, secKey, param);
            byte[] encrypt = ciper.doFinal(plain.getBytes());
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String plain) {
        try {
            IvParameterSpec param = new IvParameterSpec(VECTOR.getBytes("UTF-8"));
            SecretKeySpec secKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
            Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            ciper.init(Cipher.DECRYPT_MODE, secKey, param);
            byte[] encrypt = ciper.doFinal(Base64.getDecoder().decode(plain));
            return new String(encrypt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
