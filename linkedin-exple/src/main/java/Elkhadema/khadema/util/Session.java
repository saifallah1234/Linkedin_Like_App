package Elkhadema.khadema.util;

import Elkhadema.khadema.domain.User;

public class Session {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }

    public Session(User user) {
        Session.user = user;
    }

}
