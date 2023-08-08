package com.feedback_service.feedback_service.util;

import java.security.SecureRandom;

public class SequenceGenerator {

    private static final String DEFAULT_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!?@#$%^&*_-+=";
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final Integer DEFAULT_SECURITY_STAMP_LENGTH = 256;

    public String generateSecurityStamp() {
        return generate(DEFAULT_SECURITY_STAMP_LENGTH, DEFAULT_CHARACTERS);
    }

    public static String generatePassword(Integer targetKeyLength) {
       return generate(targetKeyLength, DEFAULT_CHARACTERS);
    }

    public static String generate(Integer targetKeyLength, String characters) {
        var random = new SecureRandom();
        var buffer = new StringBuilder(targetKeyLength);
        for (int i = 0; i < targetKeyLength; i++){
            buffer.append(characters.charAt(random.nextInt(characters.length())));
        }
        return buffer.toString();
    }
}

