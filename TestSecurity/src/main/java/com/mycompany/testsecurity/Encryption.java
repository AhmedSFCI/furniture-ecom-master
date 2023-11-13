/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testsecurity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author HP
 */
public class Encryption {

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

    public static boolean isEmptyOrNull(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof Collection) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object instanceof Map) {
            return ((Map) object).isEmpty();
        }
        if (object instanceof String) {
            return ((String) object).isBlank();
        }
        if (object instanceof StringBuilder) {
            return ((StringBuilder) object).toString().trim().length() == 0;
        }
        return false;
    }

    public static boolean isNullOrZero(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String || object instanceof StringBuilder) {
            String str = object.toString();
            if (str.isBlank()) {
                return false;
            }
            if (!str.matches("[0-9]+")) {
                return false;
            }
            Integer value = Integer.parseInt(str);
            return value == 0 || value == 1;
        }
        if (object instanceof BigDecimal) {
            return BigDecimal.ZERO.compareTo((BigDecimal) object) == 0 || BigDecimal.ONE.compareTo((BigDecimal) object) == 0;
        }
        if (object instanceof BigInteger) {
            return BigInteger.ZERO.compareTo((BigInteger) object) == 0 || BigInteger.ONE.compareTo((BigInteger) object) == 0;
        }
        if (object instanceof Number) {
            return ((Number) object).equals(0) || ((Number) object).equals(0L) || ((Number) object).equals((short) 0) || ((Number) object).equals(0.0)
                    || ((Number) object).equals(1) || ((Number) object).equals(1L) || ((Number) object).equals((short) 1) || ((Number) object).equals(1.0);
        }
        return false;
    }

    public static void main(String[] args) {

        
        String key1 = "111111";
        String key2 = "111111";
        System.out.println(" ENcrypted 1 : "+encrypt(key1));
        System.out.println(" ENcrypted 2 : "+encrypt(key2));
        
    }
}
