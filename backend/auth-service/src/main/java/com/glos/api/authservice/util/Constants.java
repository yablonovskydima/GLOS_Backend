package com.glos.api.authservice.util;

import com.glos.api.authservice.entities.Role;

import java.util.regex.Pattern;

public final class Constants {

    public static final Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role ROLE_USER = new Role(2L, "ROLE_USER");
    public static final boolean DEFAULT_IS_ACCOUNT_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ACCOUNT_NON_LOCKED = true;
    public static final boolean DEFAULT_IS_CREDENTIALS_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ENABLED = true;
    public static final boolean DEFAULT_IS_DELETED = false;
    public static final String CLAIM_ROOT_FULL_NAME = "rootFullName";
    public static final String CLAIM_CODE = "code";
    public static final String DEFAULT_CODE_SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int DEFAULT_CODE_LENGTH = 6;
    public static final int DEFAULT_EXPIRED_SECONDS = 60 * 5;
    public static final Long DEFAULT_EXPIRED_MILLIS = 18000000L;
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(\\+\\d{1,4}[-.\\s]?)(\\(\\d{1,}\\)[-\\s]?|\\d{1,}[-.\\s]?){1,}[0-9\\s]$");
    public static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9]{5,}$");

    private Constants() {}
}
