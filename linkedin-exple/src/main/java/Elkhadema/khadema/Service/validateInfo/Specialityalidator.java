package Elkhadema.khadema.Service.validateInfo;

import java.util.regex.*;

public class Specialityalidator {
    public static boolean validateText(String text) {
        String pattern = "^[a-zA-Z]+(?:[ ,][a-zA-Z]+)*$";
        return text.matches(pattern);
    }
}
