package Elkhadema.khadema.Service.validateInfo;

import java.time.LocalDate;

public class DateValidator {

    public static boolean isValidDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        if (date.isAfter(today)) {
            return false;
        }
        return true;
    }
}
