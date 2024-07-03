package com.glos.api.userservice.utils;

import com.glos.api.userservice.entities.Role;

import java.time.Duration;
import java.util.regex.Pattern;

public class Constants {
    public static final String FRIENDS_GROUP_NAME = "friends";
    public static final Long DEFAULT_ACCESS_TYPE_ID_FOR_FRIENDS = 3L;
    public static final Duration DURATION_DELETED_STATE = Duration.ofDays(5);

    public static final Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role ROLE_USER = new Role(2L, "ROLE_USER");
    public static final boolean DEFAULT_IS_ACCOUNT_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ACCOUNT_NON_LOCKED = true;
    public static final boolean DEFAULT_IS_CREDENTIALS_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ENABLED = true;
    public static final boolean DEFAULT_IS_DELETED = false;
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(\\+\\d{1,4}[-.\\s]?)(\\(\\d{1,}\\)[-\\s]?|\\d{1,}[-.\\s]?){1,}[0-9\\s]$");
    public static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9]{5,}$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%^&*(),\\.<>\\[\\]{}\"'|\\\\:;`~+\\-*\\/]).{8,}$");

}
