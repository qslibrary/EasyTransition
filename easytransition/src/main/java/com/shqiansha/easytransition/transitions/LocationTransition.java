package com.shqiansha.easytransition.transitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

import com.shqiansha.easytransition.utils.AnimatorUtils;
import com.shqiansha.easytransition.utils.MatrixUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationTransition extends Transition {
    private static final String CONTENT_LOCATION = "contentLocation";
    private static final String MATRIX = "matrix";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        saveValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        saveValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        List<Animator> animators = new ArrayList<>();
        Rect startLocation = (Rect) startValues.values.get(CONTENT_LOCATION);
        Rect endLocation = (Rect) endValues.values.get(CONTENT_LOCATION);
        Matrix endMatrix = (Matrix) endValues.values.get(MATRIX);
        if (startLocation == null || endLocation == null || endMatrix == null) return null;

        animators.add(generateTranslationXAnimator(startLocation.left, endLocation.left, endValues.view));
        animators.add(generateTranslationYAnimator(startLocation.top, endLocation.top, endValues.view));
        animators.add(generateScaleXAnimator(startLocation.width(), endLocation.width(), endMatrix, endValues.view));
        animators.add(generateScaleYAnimator(startLocation.height(), endLocation.height(), endMatrix, endValues.view));
        return AnimatorUtils.mergeAnimators(animators);
    }

    private void saveValues(TransitionValues transitionValues) {
        Map<String, Object> values = transitionValues.values;
        View view = transitionValues.view;
        Rect padding = new Rect();
        padding.left = view.getPaddingLeft();
        padding.right = view.getPaddingRight();
        padding.top = view.getPaddingTop();
        padding.bottom = view.getPaddingBottom();

        Rect location = new Rect();
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        location.left = position[0];
        location.top = position[1];
        location.right = location.left + view.getMeasuredWidth();
        location.bottom = location.top + view.getMeasuredHeight();

        Rect contentLocation = new Rect();
        contentLocation.left = location.left + padding.left;
        contentLocation.right = location.right - padding.right;
        contentLocation.top = location.top + padding.top;
        contentLocation.bottom = location.bottom - padding.bottom;

        values.put(MATRIX, view.getMatrix());
        values.put(CONTENT_LOCATION, contentLocation);
    }

    private Animator generateTranslationXAnimator(int fromLeft, int toLeft, View view) {
        return ObjectAnimator.ofFloat(view, "translationX", toLeft - fromLeft);
    }

    private Animator generateTranslationYAnimator(int fromTop, int toTop, View view) {
        return ObjectAnimator.ofFloat(view, "translationY", toTop - fromTop);
    }

    private Animator generateScaleXAnimator(float fromWidth, float toWidth, Matrix endMatrix, View view) {
        toWidth = MatrixUtils.getScaleX(endMatrix) * toWidth;
        view.setPivotX(0);
        return ObjectAnimator.ofFloat(view, "scaleX", toWidth / fromWidth);
    }

    private Animator generateScaleYAnimator(float fromHeight, float toHeight, Matrix endMatrix, View view) {
        toHeight = MatrixUtils.getScaleY(endMatrix) * toHeight;
        view.setPivotY(0);
        return ObjectAnimator.ofFloat(view, "scaleY", toHeight / fromHeight);
    }
}
