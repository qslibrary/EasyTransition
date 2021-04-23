package com.shqiansha.easytransition.transitions;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.widget.TextView;

import com.shqiansha.easytransition.core.Transition;
import com.shqiansha.easytransition.entity.ValueMap;
import com.shqiansha.easytransition.utils.MatrixUtils;
import com.shqiansha.easytransition.utils.TextViewUtils;
import com.shqiansha.easytransition.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class TextViewTransition implements Transition<TextView> {

    @Override
    public List<Animator> onCreate(ValueMap from, ValueMap to, TextView view) {
        List<Animator> animations = new ArrayList<>();
//        animations.add(generateTextSizeAnimator(from, to, view));
        animations.add(generateTextColorAnimator(from, to, view));
        animations.add(generateTranslationXAnimator(from,to,view));
        animations.add(generateTranslationYAnimator(from,to,view));
        animations.add(generateScaleXAnimator(from,to,view));
        animations.add(generateScaleYAnimator(from,to,view));
        return animations;
    }

    public Animator generateTextSizeAnimator(ValueMap from, ValueMap to, TextView view) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(view, "textSize",TextViewUtils.getTextSize(from), TextViewUtils.getTextSize(to));
        return animator;
    }

    public Animator generateTextColorAnimator(ValueMap from, ValueMap to, TextView view) {
        ObjectAnimator animator= ObjectAnimator.ofInt(view, "textColor",TextViewUtils.getTextColor(from), TextViewUtils.getTextColor(to));
        animator.setEvaluator(new ArgbEvaluator());
        return animator;
    }

    public Animator generateTranslationXAnimator(ValueMap from, ValueMap to, TextView view) {
        return ObjectAnimator.ofFloat(view, "translationX", TextViewUtils.getContentLeftMargin(to) - TextViewUtils.getContentLeftMargin(from));
    }
    public Animator generateTranslationYAnimator(ValueMap from, ValueMap to, TextView view) {
        return ObjectAnimator.ofFloat(view, "translationY", TextViewUtils.getContentTopMargin(to) - TextViewUtils.getContentTopMargin(from));
    }

    public Animator generateScaleXAnimator(ValueMap from, ValueMap to, TextView view) {
        float fromWidth =  TextViewUtils.getContentWidth(from);
        float toWidth = MatrixUtils.getScaleX(ViewUtils.getMatrix(to)) * TextViewUtils.getContentWidth(to);
        view.setPivotX(0);
        return ObjectAnimator.ofFloat(view, "scaleX", toWidth / fromWidth);
    }

    public Animator generateScaleYAnimator(ValueMap from, ValueMap to, TextView view) {
        float fromHeight =  TextViewUtils.getContentHeight(from);
        float toHeight = MatrixUtils.getScaleY(ViewUtils.getMatrix(to)) * TextViewUtils.getContentHeight(to);

        view.setPivotY(0);
        return ObjectAnimator.ofFloat(view, "scaleY", toHeight / fromHeight);
    }
}
