package org.hca.util;


public class Helper {
    public static boolean isNullOrEmptyOrWhitespace(String str) {
        if (str == null) {
            return false;
        }
        return !str.trim().isEmpty();
    }
}
