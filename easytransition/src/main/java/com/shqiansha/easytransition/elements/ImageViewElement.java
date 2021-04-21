package com.shqiansha.easytransition.elements;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shqiansha.easytransition.entity.ValueMap;
import com.shqiansha.easytransition.utils.ViewUtils;

public class ImageViewElement extends ViewElement<ImageView> {
    public static final String MATRIX="matrix";
    public static final String DRAWABLE="drawable";

    @Override
    public void onSaveViewValues(ValueMap values, ImageView view) {
        super.onSaveViewValues(values, view);
        values.put(MATRIX,view.getImageMatrix());
        values.put(DRAWABLE,view.getDrawable());
    }

    @Override
    public ImageView onCreateTransitionView(ValueMap values, FrameLayout viewGroup) {
        ImageView imageView=new ImageView(viewGroup.getContext());
        imageView.setImageDrawable(values.get(DRAWABLE, Drawable.class));
        imageView.setImageMatrix(values.get(MATRIX, Matrix.class));
        viewGroup.addView(imageView,getLayoutParam(values));
        return imageView;
    }

    public FrameLayout.LayoutParams getLayoutParam(ValueMap values) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewUtils.getContentWidth(values), ViewUtils.getContentHeight(values));
        params.leftMargin = ViewUtils.getLeftMargin(values);
        params.topMargin = ViewUtils.getTopMargin(values);
        return params;

    }
}
