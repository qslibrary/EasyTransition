package com.shqiansha.easytransition.utils;

import android.graphics.Matrix;

public class MatrixUtils {
    public static float getTranslateX(Matrix matrix){
        return getValues(matrix)[Matrix.MTRANS_X];
    }

    public static float getTranslateY(Matrix matrix){
        return getValues(matrix)[Matrix.MTRANS_Y];
    }

    public static float getScaleX(Matrix matrix){
        return getValues(matrix)[Matrix.MSCALE_X];
    }

    public static float getScaleY(Matrix matrix){
        return getValues(matrix)[Matrix.MSCALE_Y];
    }

    public static float[] getValues(Matrix matrix){
        float[] values=new float[9];
        matrix.getValues(values);
        return values;
    }
}
