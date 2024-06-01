package common.service;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class LoginSystem {
    private static final String ALGORITHM = "AES";
    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String storedUsername = "user";
    private static String storedEncryptedPassword;

    public LoginSystem() {
    }

    public static String encryptPassword(String password, String salt) throws Exception {
        SecretKey secretKey = convertStringToSecretKey(salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static boolean validatePassword(String enteredPassword, String storedSalt, String storedEncryptedPassword) throws Exception {
        SecretKey secretKey = convertStringToSecretKey(storedSalt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(storedEncryptedPassword));
        String decryptedPassword = new String(decryptedBytes);
        return enteredPassword.equals(decryptedPassword);
    }

    private static SecretKey generateSecretKey(String password, String salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    public static String generateSalt() throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static SecretKey convertStringToSecretKey(String keyString) {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }
}