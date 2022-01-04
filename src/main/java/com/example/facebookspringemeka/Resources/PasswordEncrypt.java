package com.example.facebookspringemeka.Resources;

import java.util.Arrays;
import java.util.Base64;

public class PasswordEncrypt {

    public static String encryptPassword(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
    public static String decryptPassword(String encryptedPassword){
//               byte[] password =  Base64.getMimeDecoder().decode(encryptedPassword);
//               return Arrays.toString(password);
        return new String(Base64.getMimeDecoder().decode(encryptedPassword));
    }
}
