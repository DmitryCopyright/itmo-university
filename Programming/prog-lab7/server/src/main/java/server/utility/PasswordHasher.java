package server.utility;

import server.App;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashes password.
 */
public class PasswordHasher {
    /**
     * Hashes password;.
     *
     * @param password Password itself.
     * @return Hashed password.
     */
    public static byte[] hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes());
            //   BigInteger integers = new BigInteger(1, bytes);
      //      String newPassword = integers.toString(16);
           // while (newPassword.length() < 32) {
        //        newPassword = "0" + newPassword;
      //      }
            return bytes;
        } catch (NoSuchAlgorithmException exception) {
            App.logger.error("�� ������ �������� ����������� ������!");
            throw new IllegalStateException(exception);
        }
    }
}