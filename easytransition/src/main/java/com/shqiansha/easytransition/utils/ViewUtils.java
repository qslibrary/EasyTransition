package com.shqiansha.easytransition.utils;

import com.shqiansha.easytransition.elements.ViewElement;
import com.shqiansha.easytransition.entity.ValueMap;

import static com.shqiansha.easytransition.elements.ViewElement.HEIGHT;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_BOTTOM;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_LEFT;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_RIGHT;
import static com.shqiansha.easytransition.elements.ViewElement.PADDING_TOP;
import static com.shqiansha.easytransition.elements.ViewElement.WIDTH;

public class ViewUtils {
    public static int getLeftMargin(ValueMap values) {
        return values.getInt(ViewElement.RAW_X) + values.getInt(ViewElement.PADDING_LEFT);
    }

    public static int getTopMargin(ValueMap values) {
        return values.getInt(ViewElement.RAW_Y) + values.getInt(ViewElement.PADDING_TOP);
    }

    public static int getContentWidth(ValueMap values) {
        return values.getInt(WIDTH) - values.getInt(PADDING_LEFT) - values.getInt(PADDING_RIGHT);
    }

    public static int getContentHeight(ValueMap values) {
        return values.getInt(HEIGHT) - values.getInt(PADDING_TOP) - values.getInt(PADDING_BOTTOM);
    }
}
