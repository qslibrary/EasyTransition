package com.shqiansha.easytransition.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;

import java.util.List;

public class AnimatorUtils {
    public static Animator mergeAnimators(List<Animator> animators) {
        AnimatorSet set=new AnimatorSet();
        set.playTogether(animators);
        return set;
    }
}
