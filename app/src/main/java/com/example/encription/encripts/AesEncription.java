package com.example.encription.encripts;

import android.util.Base64;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesEncription {

    String outputString ;
    static String  AES = "AES";

    public static String decript(String outputString,String password)throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodevslue = Base64.decode(outputString,Base64.DEFAULT);
        byte[] decvalue = c.doFinal(decodevslue);
        String decrypte =new String(decvalue);
        return decrypte;
    }

    public static String encript(String Data ,String password) throws  Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal;
        encVal = c.doFinal(Data.getBytes());
        String encriptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encriptedValue;
    }

    /**
     * generating the key using AES encription 256 bit;
     */
    private static SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key =digest.digest();
        SecretKeySpec secretKeySpec =new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }

}
