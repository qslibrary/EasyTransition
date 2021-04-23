package com.shqiansha.easytransition.elements;

import android.view.View;

import com.shqiansha.easytransition.core.Element;
import com.shqiansha.easytransition.entity.ValueMap;

public abstract class ViewElement<T extends View> implements Element<T> {
    public static final String PADDING_LEFT = "paddingLeft";
    public static final String PADDING_RIGHT = "paddingRight";
    public static final String PADDING_TOP = "paddingTop";
    public static final String PADDING_BOTTOM = "paddingBottom";

    public static final String RAW_X = "rawX";
    public static final String RAW_Y = "rawY";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";

    public static final String MATRIX = "matrix";

    @Override
    public void onSaveViewValues(ValueMap values, T view) {
        values.put(PADDING_LEFT, view.getPaddingLeft());
        values.put(PADDING_RIGHT, view.getPaddingRight());
        values.put(PADDING_TOP, view.getPaddingTop());
        values.put(PADDING_BOTTOM, view.getPaddingBottom());

        int[] position = new int[2];
        view.getLocationOnScreen(position);
        values.put(RAW_X, position[0]);
        values.put(RAW_Y, position[1]);
        values.put(WIDTH, view.getMeasuredWidth());
        values.put(HEIGHT, view.getMeasuredHeight());

        values.put(MATRIX, view.getMatrix());

    }

    @Override
    public void onSaveViewSharedValues(ValueMap sharedValues, T view) {

    }

    @Override
    public void onDestroyTransitionView(T view) {

    }
}
