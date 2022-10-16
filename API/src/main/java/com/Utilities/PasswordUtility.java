package com.Utilities;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtility {
    final static private int ITERATION_COUNT = 10000;
    final static private int KEY_LENGTH = 256;
    

    public static byte[] generatePasswordSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomSaltBytes = new byte[16];
        secureRandom.nextBytes(randomSaltBytes);

        return randomSaltBytes;
    }


    public static String generateEncryptedPassword(String plainPassword, byte[] salt)  {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(plainPassword.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            return Base64.getEncoder().encodeToString(hash); 
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new AssertionError("Error appeared during hashing time: " + e.getMessage());
        } finally {
            pbeKeySpec.clearPassword();
        }
    }
}
