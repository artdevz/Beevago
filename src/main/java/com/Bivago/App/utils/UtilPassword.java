package com.Bivago.App.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilPassword {
    
    public static String md5(String password) throws NoSuchAlgorithmException {
        MessageDigest messagedig = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, messagedig.digest(password.getBytes()));
        return hash.toString(16);

    }

}
