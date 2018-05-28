package com.example.misc2.utils;

public class ExceptionUtils {
    public static String parseException(Exception exception) {
        int length1 = exception.getMessage().length();
        int length2 = exception.toString().length();

        StringBuilder res = new StringBuilder(length1 + length2);
        res.append(exception.getMessage());
        res.append("\n");
        res.append(exception.toString());
        return res.toString();
    }
}
