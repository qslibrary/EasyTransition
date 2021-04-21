package com.shqiansha.easytransition.utils;

public class StringUtils {
    public static boolean isEqual(String first, String second) {
        if (first == null) return second == null;
        else return first.equals(second);
    }
}
