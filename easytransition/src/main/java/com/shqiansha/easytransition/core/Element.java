package com.shqiansha.easytransition.core;

import android.view.View;
import android.widget.FrameLayout;

import com.shqiansha.easytransition.entity.ValueMap;

import java.util.HashMap;

public interface Element<T extends View> {

    void onSaveViewValues(ValueMap values, T view);

    T onCreateTransitionView(ValueMap values, FrameLayout viewGroup);

}
