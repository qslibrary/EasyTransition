package com.shqiansha.easytransition.core;

import android.animation.Animator;
import android.view.View;

import com.shqiansha.easytransition.entity.ValueMap;

import java.util.List;

public interface Transition<T extends View> {
    List<Animator> onCreate(ValueMap from, ValueMap to, T view);
}
