package com.shqiansha.easytransition.entity;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class ValueMap {
    private HashMap<String, Object> values = new HashMap<>();

    @Nullable
    public <K> K get(String name) {
        Object obj = values.get(name);
        if (obj != null) return (K) obj;
        return null;
    }

    @Nullable
    public <K> K get(String name, Class<K> clz) {
        Object obj = values.get(name);
        if (clz.isInstance(obj)) {
            return clz.cast(obj);
        } else {
            return null;
        }
    }

    public void put(String key, Object value) {
        values.put(key, value);
    }

    public int getInt(String name) {
        Object obj = values.get(name);
        return obj == null ? 0 : (int) obj;
    }

    public float getFloat(String name) {
        Object obj = values.get(name);
        return obj == null ? 0 : (float) obj;
    }

    public String getString(String name) {
        Object obj = values.get(name);
        return obj == null ? "" : (String) obj;
    }
}
