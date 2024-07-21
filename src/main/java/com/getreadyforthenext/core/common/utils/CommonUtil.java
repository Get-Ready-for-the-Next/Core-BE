package com.getreadyforthenext.core.common.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CommonUtil {

    public String generateRandomString(int length) {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        final SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int position = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(position));
        }

        return password.toString();
    }
}
