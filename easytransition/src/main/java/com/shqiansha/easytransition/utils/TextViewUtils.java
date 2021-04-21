package com.shqiansha.easytransition.utils;

import android.view.Gravity;

import com.shqiansha.easytransition.elements.TextViewElement;
import com.shqiansha.easytransition.entity.ValueMap;

import static com.shqiansha.easytransition.elements.TextViewElement.TEXT_HEIGHT;
import static com.shqiansha.easytransition.elements.ViewElement.HEIGHT;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_BOTTOM;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_LEFT;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_RIGHT;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_TOP;
import static com.shqiansha.easytransition.elements.ViewElement.RAW_X;
import static com.shqiansha.easytransition.elements.ViewElement.RAW_Y;
import static com.shqiansha.easytransition.elements.ViewElement.WIDTH;
import static com.shqiansha.easytransition.elements.TextViewElement.TEXT_WIDTH;
import static com.shqiansha.easytransition.elements.TextViewElement.GRAVITY;

public class TextViewUtils {

    public static int getContentTopMargin(ValueMap values) {
        int gravity = values.getInt(GRAVITY) & Gravity.VERTICAL_GRAVITY_MASK;
        switch (gravity) {
            case Gravity.TOP:
                return values.getInt(PADDING_TOP) + values.getInt(RAW_Y);
            case Gravity.BOTTOM:
                return values.getInt(RAW_Y) + values.getInt(HEIGHT) - (int) values.getFloat(TEXT_HEIGHT) - values.getInt(PADDING_BOTTOM);
            case Gravity.CENTER_VERTICAL:
                int height = values.getInt(HEIGHT) - values.getInt(PADDING_TOP) - values.getInt(PADDING_BOTTOM);
                return values.getInt(PADDING_TOP) + values.getInt(RAW_Y) + height / 2 - (int) values.getFloat(TEXT_HEIGHT) / 2;
        }
        return 0;
    }

    public static int getContentLeftMargin(ValueMap values) {
        int gravity = values.getInt(GRAVITY) & Gravity.HORIZONTAL_GRAVITY_MASK;
        switch (gravity) {
            case Gravity.LEFT:
                return values.getInt(PADDING_LEFT) + values.getInt(RAW_X);
            case Gravity.RIGHT:
                return values.getInt(RAW_X) + values.getInt(WIDTH) - (int) values.getFloat(TEXT_WIDTH) - values.getInt(PADDING_RIGHT);
            case Gravity.CENTER_HORIZONTAL:
                int width = values.getInt(WIDTH) - values.getInt(PADDING_LEFT) - values.getInt(PADDING_RIGHT);
                return values.getInt(PADDING_LEFT) + values.getInt(RAW_X) + width / 2 - (int) values.getFloat(TEXT_WIDTH) / 2;
        }
        return 0;
    }

    public static int getContentWidth(ValueMap values) {
        return values.getInt(WIDTH) - values.getInt(PADDING_LEFT) - values.getInt(PADDING_RIGHT);
    }

    public static int getContentHeight(ValueMap values) {
        return values.getInt(HEIGHT) - values.getInt(PADDING_TOP) - values.getInt(PADDING_BOTTOM);
    }

    public static float getTextSize(ValueMap values) {
        return values.getFloat(TextViewElement.TEXT_SIZE);
    }
    public static int getTextColor(ValueMap values) {
        return values.getInt(TextViewElement.TEXT_COLOR);
    }
}
