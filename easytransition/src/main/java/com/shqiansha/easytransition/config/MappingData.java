package com.shqiansha.easytransition.config;

import android.view.View;

import com.shqiansha.easytransition.core.Element;

import java.util.HashMap;
import java.util.Set;

/**
 * it is a Hash Map which caches supported view and it's element
 * {@link Element}
 */
public class MappingData {

    private static HashMap<Class<? extends View>, Class<? extends Element>> data = new HashMap<>();

    public static void register(Class<? extends View> viewClass, Class<? extends Element> elementClass) {
        data.put(viewClass, elementClass);
    }

    public static Class get(Class viewClass) {
        return data.get(viewClass);
    }

    public static Set<Class<? extends View>> keys() {
        return data.keySet();
    }
}
