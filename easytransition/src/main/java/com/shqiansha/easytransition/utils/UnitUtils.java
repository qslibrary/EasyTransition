package com.shqiansha.easytransition.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class UnitUtils {
    public static float toSp(Context context, float px) {
        return px / getMetrics(context).scaledDensity;
    }

    public static float toDp(Context context, float px) {
        return px / getMetrics(context).density;
    }

    public static float dpToPx(Context context, float dp) {
        return TypedValue.applyDimension(COMPLEX_UNIT_DIP, dp, getMetrics(context));
    }

    public static float spToPx(Context context, float sp) {
        return TypedValue.applyDimension(COMPLEX_UNIT_SP, sp, getMetrics(context));
    }


    private static DisplayMetrics getMetrics(Context c) {
        Resources r = c != null ? c.getResources() : Resources.getSystem();
        return r.getDisplayMetrics();
    }
}
