package Elkhadema.khadema.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    public static String encryptPassword(String username, String password) {
        try {
            String salt = username;
            String saltedPassword = password + salt;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    saltedPassword.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifyPassword(String username, String inputPassword, String storedPassword) {
        String hashedInputPassword = encryptPassword(username, inputPassword);
        return hashedInputPassword.equals(storedPassword);
    }

    public static void main(String[] args) {
        String username = "example_user";
        String password = "your_password_here";
        String storedPassword = encryptPassword(username, password);

        // Later, when verifying the password
        String userInputPassword = "user_input_password";
        boolean passwordMatches = verifyPassword(username, userInputPassword, storedPassword);
        System.out.println("Password matches: " + passwordMatches);
    }

}
