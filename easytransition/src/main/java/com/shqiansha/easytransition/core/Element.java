package com.shqiansha.easytransition.core;

import android.view.View;
import android.widget.FrameLayout;

import com.shqiansha.easytransition.entity.ValueMap;

import java.util.HashMap;

/**
 * element is similar to view,but it is mainly used to saveViewValues and createTransitionView
 * if you want to support special view, you need to implement this and register the special view and your element
 * in  MappingData.{@link com.shqiansha.easytransition.config.MappingData#register(Class, Class)}
 * like this : MappingData.register(TextView.class,TextVieElement.class)
 *
 * @param <T>
 */
public interface Element<T extends View> {

    /**
     * @param values cache map
     * @param view   original view
     */
    void onSaveViewValues(ValueMap values, T view);

    /**
     * @param values    cache map
     * @param viewGroup parent
     * @return
     */
    T onCreateTransitionView(ValueMap values, FrameLayout viewGroup);

}
