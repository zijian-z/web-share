package com.zijian.news.util;

import java.util.Random;

public class VerificationCodeUtil {
    private static final char[] codeChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String generate() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            builder.append(codeChar[random.nextInt(62)]);
        }
        return builder.toString();
    }
}
