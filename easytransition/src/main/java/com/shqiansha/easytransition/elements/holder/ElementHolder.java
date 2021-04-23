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

    private ValueMap values = new ValueMap();
    private Element<T> element;

    public void saveValues(T view) {
        if (element != null) {
            setActivity(ActivityUtils.getLast());
            setId(view.getId());
            element.onSaveViewValues(values, view);
        }
    }

    public ValueMap getValues() {
        return values;
    }

    public int getId() {
        return (int) values.get(KEY_ID);
    }

    public void setId(@IdRes int id) {
        values.put(KEY_ID, id);
    }

    public String getTag(){
        return values.get(KEY_TAG,String.class);
    }

    public void setTag(String tag){
        values.put(KEY_TAG,tag);
    }

    public boolean isLazy(){
        Object lazy=values.get(KEY_LAZY);
        return lazy != null && (boolean) lazy;
    }

    public void setLazy(Boolean lazy){
        values.put(KEY_LAZY,lazy);
    }

    public void setActivity(String activityName) {
        if (values.get(KEY_ACTIVITY) == null) {
            values.put(KEY_ACTIVITY, activityName);
        }
    }

    public boolean isActivityEqual(Activity activity) {
        return activity.getClass().getName().equals(values.get(KEY_ACTIVITY));
    }

    @Nullable
    public String getActivity() {
        return values.getString(KEY_ACTIVITY);
    }

    public T getTransitionView(FrameLayout decor) {
        return element.onCreateTransitionView(values, decor);
    }

    public void setElement(Element<T> element) {
        this.element = element;
    }
}
