package com.shqiansha.easytransition.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TagUtils {
    public static final String TAG_START = "ET";
    private static Set<String> map = new HashSet<>();

    public static String generateTag() {
        String tag;
        do {
            tag = TAG_START + new Random().nextInt(1000);
        } while (!map.add(tag));
        return tag;
    }

    public static void remove(String tag) {
        map.remove(tag);
    }
}
