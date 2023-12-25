package com.service;

import com.exception.InvalidSaltException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordEncryptionService {

    private static final int KEY_LENGTH=256;

    public static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final Random RANDOM=new SecureRandom();

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS=10000;

    public static byte[] hash(char[] password,byte[] salt){
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password,Character.MIN_VALUE);
        try{
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new AssertionError("Error during hashing the password "+e.getMessage(),e);
        }
        finally {
            pbeKeySpec.clearPassword();
        }
    }

    public static String encryptWithSalt(String password, String salt){
        if (salt==null||salt.isEmpty()){
            throw new InvalidSaltException("Salt cannot be empty or null");
        }

        byte[] saltPassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(saltPassword);
    }

    public static boolean isPasswordValid(String loginPassword,String securedUserDbPassword,String salt){
        String newSecuredPassword = encryptWithSalt(loginPassword, salt);

        //checking whether the new encrypted login password and secured password are equal or not
        return newSecuredPassword.equalsIgnoreCase(securedUserDbPassword);
    }
    public static String generateSalt(int length,String password){
        StringBuilder saltString = new StringBuilder(length);
        for (int i=0;i<length;i++){
            saltString.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(saltString);

    }

}
