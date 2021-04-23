package com.shqiansha.easytransition.core;

import android.animation.Animator;
import android.view.View;

import com.shqiansha.easytransition.entity.ValueMap;

import java.util.List;

/**
 * transition is a list of animator
 * @param <T>
 */
public interface Transition<T extends View> {
    /**
     *
     * @param from from cache data
     * @param to to cache data
     * @param view transition view
     * @return
     */
    List<Animator> onCreate(ValueMap from, ValueMap to, T view);
}
