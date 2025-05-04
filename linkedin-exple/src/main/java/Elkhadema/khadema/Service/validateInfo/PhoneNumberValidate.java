package Elkhadema.khadema.Service.validateInfo;

import java.util.regex.*;

public class PhoneNumberValidate {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}