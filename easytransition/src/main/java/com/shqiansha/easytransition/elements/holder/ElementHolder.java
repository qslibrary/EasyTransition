package com.shqiansha.easytransition.elements.holder;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.shqiansha.easytransition.entity.ValueMap;
import com.shqiansha.easytransition.utils.ActivityUtils;
import com.shqiansha.easytransition.core.Element;

public class ElementHolder<T extends View> {
    private static final String KEY_ID = "id";
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_TAG = "tag";
    private static final String KEY_LAZY = "lazy";

    private ValueMap mValues = new ValueMap();
    private ValueMap mSharedValues = new ValueMap();
    private Element<T> element;

    public void saveValues(T view) {
        if (element != null) {
            setActivity(ActivityUtils.getLast());
            setId(view.getId());
            element.onSaveViewValues(mValues, view);
        }
    }

    public void saveSharedValues(T view){
        if (element != null) {
            element.onSaveViewSharedValues(mSharedValues, view);
        }
    }

    public ValueMap getValues() {
        return mValues;
    }

    public int getId() {
        Integer id=mValues.get(KEY_ID,Integer.class);
        return id==null?0:id;
    }

    public void setId(@IdRes int id) {
        mValues.put(KEY_ID, id);
    }

    public String getTag() {
        return mValues.get(KEY_TAG, String.class);
    }

    public void setTag(String tag) {
        mValues.put(KEY_TAG, tag);
    }

    public boolean isLazy() {
        Object lazy = mValues.get(KEY_LAZY);
        return lazy != null && (boolean) lazy;
    }

    public void setLazy(Boolean lazy) {
        mValues.put(KEY_LAZY, lazy);
    }

    public void setActivity(String activityName) {
        if (mValues.get(KEY_ACTIVITY) == null) {
            mValues.put(KEY_ACTIVITY, activityName);
        }
    }

    public boolean isActivityEqual(Activity activity) {
        return activity.getClass().getName().equals(mValues.get(KEY_ACTIVITY));
    }

    @Nullable
    public String getActivity() {
        return mValues.getString(KEY_ACTIVITY);
    }

    public T getTransitionView(FrameLayout decor) {
        return element.onCreateTransitionView(mValues, mSharedValues, decor);
    }

    public void setElement(Element<T> element) {
        this.element = element;
    }
}
