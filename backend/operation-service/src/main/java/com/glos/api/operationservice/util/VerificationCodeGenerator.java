package com.glos.api.operationservice.util;

import com.glos.api.operationservice.Operations;

import java.security.SecureRandom;

public final class VerificationCodeGenerator {

    private static VerificationCodeGenerator instance;


    public static VerificationCodeGenerator getInstance() {
        return (instance != null) ? instance : new VerificationCodeGenerator();
    }

    private VerificationCodeGenerator() {}

    public String generateVerificationCode() {
        final SecureRandom sRandom = new SecureRandom();
        final StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < Operations.DEFAULT_CODE_LENGTH; i++) {
            final int randomIndex = sRandom.nextInt(Operations.DEFAULT_CODE_SYMBOLS.length());
            verificationCode.append(Operations.DEFAULT_CODE_SYMBOLS.charAt(randomIndex));
        }
        return verificationCode.toString();
    }

}
