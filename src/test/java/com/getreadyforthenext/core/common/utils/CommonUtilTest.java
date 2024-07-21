package com.getreadyforthenext.core.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonUtilTest {

    @Test
    void generateRandomString() {
        int length = 32;
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        CommonUtil commonUtil = new CommonUtil();
        String password = commonUtil.generateRandomString(length);

        assertNotNull(password, "Password should not be null");

        for (char c : password.toCharArray()) {
            assertTrue(CHARACTERS.indexOf(c) >= 0, "Password contains invalid character: " + c);
        }
    }
}
