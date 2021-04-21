package com.shqiansha.easytransition.transitions;

import android.animation.Animator;
import android.view.View;

import com.shqiansha.easytransition.entity.ValueMap;

import java.util.ArrayList;
import java.util.List;

public class CommonViewTransition extends ViewTransition<View> {
    @Override
    public List<Animator> onCreate(ValueMap from, ValueMap to, View view) {
        List<Animator> animators = new ArrayList<>();
        animators.add(generateTranslationXAnimator(from,to,view));
        animators.add(generateTranslationYAnimator(from,to,view));
        return animators;
    }
}
