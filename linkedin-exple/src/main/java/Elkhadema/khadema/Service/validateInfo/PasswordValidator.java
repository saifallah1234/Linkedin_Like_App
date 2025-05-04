package Elkhadema.khadema.Service.validateInfo;

public class PasswordValidator {
    public static boolean isValidPassword(String password) {
        if (password.length() < 8)
            return false;

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) {
                hasLetter = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isWhitespace(ch)) {
                hasSpecialChar = true;
            }
        }
        return hasLetter && hasDigit && hasSpecialChar;
    }
}
