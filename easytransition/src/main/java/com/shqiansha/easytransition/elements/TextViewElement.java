package com.shqiansha.easytransition.elements;

import android.graphics.Typeface;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shqiansha.easytransition.entity.ValueMap;
import com.shqiansha.easytransition.utils.TextViewUtils;
import com.shqiansha.easytransition.utils.UnitUtils;


public class TextViewElement extends ViewElement<TextView> {
    public static final String TEXT_SIZE = "textSize";
    public static final String TEXT_COLOR = "textColor";
    public static final String TEXT_TYPEFACE = "textTypeface";
    public static final String TEXT_VALUE = "text";
    public static final String TEXT_WIDTH = "textWidth";
    public static final String TEXT_HEIGHT = "textHeight";
    public static final String GRAVITY = "gravity";

    @Override
    public void onSaveViewValues(ValueMap values, TextView view) {
        super.onSaveViewValues(values, view);
        values.put(TEXT_SIZE, UnitUtils.toSp(view.getContext(), view.getTextSize()));
        values.put(TEXT_COLOR, view.getCurrentTextColor());
        values.put(TEXT_TYPEFACE, view.getTypeface());
        values.put(TEXT_VALUE, view.getText());

        values.put(GRAVITY, view.getGravity());

        values.put(TEXT_HEIGHT, view.getPaint().getFontMetrics().bottom - view.getPaint().getFontMetrics().top);
        values.put(TEXT_WIDTH, view.getPaint().measureText(view.getText().toString()));

        //adjust TextView drawable
        values.put(PADDING_LEFT, view.getTotalPaddingLeft());
        values.put(PADDING_RIGHT, view.getTotalPaddingRight());
        values.put(PADDING_BOTTOM, view.getTotalPaddingBottom());
        values.put(PADDING_TOP, view.getTotalPaddingTop());
    }

    @Override
    public TextView onCreateTransitionView(ValueMap values, FrameLayout viewGroup) {
        TextView textView = new TextView(viewGroup.getContext());
        textView.setTextSize(values.getFloat(TEXT_SIZE));
        textView.setText(values.getString(TEXT_VALUE));
        textView.setTextColor(values.getInt(TEXT_COLOR));
        textView.setTypeface(values.get(TEXT_TYPEFACE, Typeface.class));
        textView.setTextColor(values.getInt(TEXT_COLOR));
        viewGroup.addView(textView, TextViewUtils.generateParams(values));
        return textView;
    }
}
