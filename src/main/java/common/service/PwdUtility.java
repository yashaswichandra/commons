package common.service;

import common.dao.Password;

public class PwdUtility {

    public static Password EncryptPassword(String enterPassword) {
        try {
            String salt = LoginSystem.generateSalt();
            String storedEncryptedPassword = LoginSystem.encryptPassword(enterPassword, salt);
            Password dataClass = Password.build(salt, storedEncryptedPassword);
            return dataClass;
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return null;
        }
    }

    public static boolean Verify(String enteredPassword, String storedSalt, String storedEncryptedPassword) {
        try {
            return LoginSystem.validatePassword(enteredPassword, storedSalt, storedEncryptedPassword);
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return false;
        }
    }
}
