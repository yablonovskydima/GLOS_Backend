package com.glos.accessservice.utils;

import java.security.SecureRandom;

public class VerificationCodeGenerator
{
    public String generateVerificationCode() {
        final SecureRandom sRandom = new SecureRandom();
        final StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < Constants.DEFAULT_CODE_LENGTH; i++) {
            final int randomIndex = sRandom.nextInt(Constants.DEFAULT_CODE_SYMBOLS.length());
            verificationCode.append(Constants.DEFAULT_CODE_SYMBOLS.charAt(randomIndex));
        }
        return verificationCode.toString();
    }
}
