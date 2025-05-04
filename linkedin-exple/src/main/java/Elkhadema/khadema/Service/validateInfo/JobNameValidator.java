package Elkhadema.khadema.Service.validateInfo;

public class JobNameValidator {

    public static boolean isValidJobName(String jobName) {
        if (jobName == null || jobName.trim().isEmpty()) {
            return false;
        }

        String regex = "^[a-zA-Z0-9\\s]+$";

        return jobName.matches(regex);
    }
}
