package com.glos.api.authservice.util;

public class UsernameUtil {

    public static String detectTypeLogin(String login) {
        if (Constants.EMAIL_PATTERN.matcher(login).find()) {
            return "email";
        } else if (Constants.PHONE_NUMBER_PATTERN.matcher(login).find()) {
            return "phoneNumber";
        } else if (Constants.USERNAME_PATTERN.matcher(login).find()) {
            return "username";
        } else {
            return null;
        }
    }

}
