package com.shqiansha.easytransition.elements;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shqiansha.easytransition.entity.ValueMap;
import com.shqiansha.easytransition.utils.MatrixUtils;

public class ImageViewElement extends ViewElement<ImageView> {
    public static final String IMAGE_MATRIX = "imageMatrix";
    public static final String DRAWABLE = "drawable";

    @Override
    public void onSaveViewValues(ValueMap values, ImageView view) {
        super.onSaveViewValues(values, view);
        values.put(IMAGE_MATRIX, view.getImageMatrix());
        values.put(DRAWABLE, view.getDrawable());
    }

    @Override
    public ImageView onCreateTransitionView(ValueMap values, FrameLayout viewGroup) {
        ImageView imageView = new ImageView(viewGroup.getContext());
        imageView.setImageDrawable(values.get(DRAWABLE, Drawable.class));
        imageView.setImageMatrix(values.get(IMAGE_MATRIX, Matrix.class));
        Matrix matrix = values.get(MATRIX, Matrix.class);
        imageView.setPivotX(0);
        imageView.setPivotY(0);
        imageView.setScaleX(MatrixUtils.getScaleX(matrix));
        imageView.setScaleY(MatrixUtils.getScaleY(matrix));
        return imageView;
    }
}
