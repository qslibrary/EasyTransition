package com.shqiansha.easytransition.transitions;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;


import com.shqiansha.easytransition.utils.AnimatorUtils;
import com.shqiansha.easytransition.utils.MatrixUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextViewTransition extends Transition {
    private static final String TEXT_COLOR = "textColor";
    private static final String PADDING = "padding";
    private static final String LOCATION = "location";
    private static final String TEXT_LOCATION = "textLocation";
    private static final String GRAVITY = "gravity";
    private static final String MATRIX = "matrix";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            Map<String, Object> values = transitionValues.values;
            TextView view = (TextView) transitionValues.view;
            saveValues(view, values);
        }
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            Map<String, Object> values = transitionValues.values;
            TextView view = (TextView) transitionValues.view;
            saveValues(view, values);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (endValues != null && endValues.view instanceof TextView) {
            List<Animator> animators = new ArrayList<>();
            TextView view = (TextView) endValues.view;
            Rect startTextLocation = (Rect) startValues.values.get(TEXT_LOCATION);
            Rect endTextLocation = (Rect) endValues.values.get(TEXT_LOCATION);
            Matrix endMatrix=(Matrix) endValues.values.get(MATRIX);
            ObjectAnimator colorAnimator = ObjectAnimator.ofInt(view, "textColor", (int) startValues.values.get(TEXT_COLOR), (int) endValues.values.get(TEXT_COLOR));
            colorAnimator.setEvaluator(new ArgbEvaluator());
            animators.add(colorAnimator);
            animators.add(generateTranslationXAnimator(startTextLocation,endTextLocation,view));
            animators.add(generateTranslationYAnimator(startTextLocation,endTextLocation,view));
            animators.add(generateScaleXAnimator(startTextLocation,endTextLocation,endMatrix,view));
            animators.add(generateScaleYAnimator(startTextLocation,endTextLocation,endMatrix,view));
            return AnimatorUtils.mergeAnimators(animators);
        }
        return null;
    }

    private void saveValues(TextView view, Map<String, Object> values) {
        Rect padding = new Rect();
        padding.left = view.getTotalPaddingLeft();
        padding.right = view.getTotalPaddingRight();
        padding.top = view.getTotalPaddingTop();
        padding.bottom = view.getTotalPaddingBottom();

        Rect location = new Rect();
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        location.left = position[0];
        location.top = position[1];
        location.right = location.left + view.getMeasuredWidth();
        location.bottom = location.top + view.getMeasuredHeight();

        Rect textLocation = new Rect();
        int textWidth = (int) view.getPaint().measureText(view.getText().toString());
        int textHeight = (int) (view.getPaint().getFontMetrics().bottom - view.getPaint().getFontMetrics().top);
        textLocation.left = getTextLeftMargin(view.getGravity(), location, padding, textWidth);
        textLocation.top = getTextTopMargin(view.getGravity(), location, padding, textHeight);
        textLocation.right = textLocation.left + textWidth;
        textLocation.bottom = textLocation.top + textHeight;

        values.put(TEXT_COLOR, view.getCurrentTextColor());
        values.put(GRAVITY, view.getGravity());
        values.put(MATRIX, view.getMatrix());
        values.put(PADDING, padding);
        values.put(LOCATION, location);
        values.put(TEXT_LOCATION, textLocation);
    }

    public Animator generateTranslationXAnimator(Rect fromTextLocation, Rect toTextLocation, TextView view) {
        if (fromTextLocation == null || toTextLocation == null) return null;
        return ObjectAnimator.ofFloat(view, "translationX", toTextLocation.left - fromTextLocation.left);
    }

    public Animator generateTranslationYAnimator(Rect fromTextLocation, Rect toTextLocation, TextView view) {
        if (fromTextLocation == null || toTextLocation == null) return null;
        return ObjectAnimator.ofFloat(view, "translationY", toTextLocation.top - fromTextLocation.top);
    }

    public Animator generateScaleXAnimator(Rect fromTextLocation, Rect toTextLocation,Matrix matrix, TextView view) {
        if (fromTextLocation == null || toTextLocation == null) return null;
        float fromWidth = fromTextLocation.width();
        float toWidth = MatrixUtils.getScaleX(matrix) * toTextLocation.width();
        view.setPivotX(0);
        return ObjectAnimator.ofFloat(view, "scaleX", toWidth / fromWidth);
    }

    public Animator generateScaleYAnimator(Rect fromTextLocation, Rect toTextLocation,Matrix matrix, TextView view) {
        if (fromTextLocation == null || toTextLocation == null) return null;
        float fromHeight = fromTextLocation.height();
        float toHeight = MatrixUtils.getScaleY(matrix) * toTextLocation.height();

        view.setPivotY(0);
        return ObjectAnimator.ofFloat(view, "scaleY", toHeight / fromHeight);
    }

    private static int getTextLeftMargin(int gravity, Rect location, Rect padding, int textWidth) {
        gravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        switch (gravity) {
            case Gravity.LEFT:
                return padding.left + location.left;
            case Gravity.RIGHT:
                return location.right - padding.right - textWidth;
            case Gravity.CENTER_HORIZONTAL:
                int width = location.width() - padding.left - padding.right;
                return location.left + padding.left + width / 2 - textWidth / 2;
        }
        return 0;
    }

    private static int getTextTopMargin(int gravity, Rect location, Rect padding, int textHeight) {
        gravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
        switch (gravity) {
            case Gravity.TOP:
                return location.top + padding.top;
            case Gravity.BOTTOM:
                return location.bottom - padding.bottom - textHeight;
            case Gravity.CENTER_VERTICAL:
                int height = location.height() - padding.top - padding.bottom;
                return location.top + padding.top + height / 2 - textHeight / 2;
        }
        return 0;
    }
}
