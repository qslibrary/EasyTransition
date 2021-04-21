package com.shqiansha.easytransition.transitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.TextView;

import com.shqiansha.easytransition.core.Transition;
import com.shqiansha.easytransition.elements.ViewElement;
import com.shqiansha.easytransition.entity.ValueMap;
import com.shqiansha.easytransition.utils.TextViewUtils;
import com.shqiansha.easytransition.utils.ViewUtils;


public abstract class ViewTransition<T extends View> implements Transition<T> {

    public Animator generateTranslationXAnimator(ValueMap from, ValueMap to, T view) {
        return ObjectAnimator.ofFloat(view, "translationX", ViewUtils.getLeftMargin(to) - ViewUtils.getLeftMargin(from));
    }

    public Animator generateTranslationYAnimator(ValueMap from, ValueMap to, T view) {
        return ObjectAnimator.ofFloat(view, "translationY", ViewUtils.getTopMargin(to) - ViewUtils.getTopMargin(from));
    }

    public Animator generateScaleXAnimator(ValueMap from, ValueMap to, T view) {
        view.setPivotX(0);
        return ObjectAnimator.ofFloat(view, "scaleX", (float) ViewUtils.getContentWidth(to) / ViewUtils.getContentWidth(from));
    }

    public Animator generateScaleYAnimator(ValueMap from, ValueMap to, T view) {
        view.setPivotY(0);
        return ObjectAnimator.ofFloat(view, "scaleY", (float) ViewUtils.getContentHeight(to) / ViewUtils.getContentHeight(from));
    }
}
