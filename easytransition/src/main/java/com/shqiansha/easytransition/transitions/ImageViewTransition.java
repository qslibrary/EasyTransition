package com.shqiansha.easytransition.transitions;

import android.animation.Animator;
import android.widget.ImageView;

import com.shqiansha.easytransition.entity.ValueMap;

import java.util.ArrayList;
import java.util.List;

public class ImageViewTransition extends ViewTransition<ImageView> {
    @Override
    public List<Animator> onCreate(ValueMap from, ValueMap to, ImageView view) {
        List<Animator> animators = new ArrayList<>();
        animators.add(generateTranslationXAnimator(from, to, view));
        animators.add(generateTranslationYAnimator(from, to, view));
        animators.add(generateScaleXAnimator(from, to, view));
        animators.add(generateScaleYAnimator(from, to, view));
        return animators;
    }
}
