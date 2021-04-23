package com.shqiansha.easytransition.transitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.shqiansha.easytransition.core.Transition;
import com.shqiansha.easytransition.entity.ValueMap;
import com.shqiansha.easytransition.utils.MatrixUtils;
import com.shqiansha.easytransition.utils.ViewUtils;


public abstract class ViewTransition<T extends View> implements Transition<T> {

    public Animator generateTranslationXAnimator(ValueMap from, ValueMap to, T view) {
        float end = ViewUtils.getLeftMargin(to) - ViewUtils.getLeftMargin(from);
        return ObjectAnimator.ofFloat(view, "translationX", end);
    }

    public Animator generateTranslationYAnimator(ValueMap from, ValueMap to, T view) {
        float end = ViewUtils.getTopMargin(to) - ViewUtils.getTopMargin(from);
        return ObjectAnimator.ofFloat(view, "translationY", end);
    }

    public Animator generateScaleXAnimator(ValueMap from, ValueMap to, T view) {
        float fromWidth =  ViewUtils.getContentWidth(from);
        float toWidth = MatrixUtils.getScaleX(ViewUtils.getMatrix(to)) * ViewUtils.getContentWidth(to);
        view.setPivotX(0);
        return ObjectAnimator.ofFloat(view, "scaleX", toWidth / fromWidth);
    }

    public Animator generateScaleYAnimator(ValueMap from, ValueMap to, T view) {
        float fromHeight =  ViewUtils.getContentHeight(from);
        float toHeight = MatrixUtils.getScaleY(ViewUtils.getMatrix(to)) * ViewUtils.getContentHeight(to);

        view.setPivotY(0);
        return ObjectAnimator.ofFloat(view, "scaleY", toHeight / fromHeight);
    }
}
