package Elkhadema.khadema.Service.validateInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator {

    private static final String URL_REGEX = "^((https?)://)?([\\w.-]+\\.\\w{2,})(/\\S*)?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public static boolean validateURL(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

}
