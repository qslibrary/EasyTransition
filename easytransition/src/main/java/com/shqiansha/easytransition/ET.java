package com.shqiansha.easytransition;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import com.shqiansha.easytransition.core.TransitionManager;

public class ET {
    /**
     * initialize EasyTransition
     *
     * @param app application
     */
    public static void init(Application app) {
        TransitionManager.getInstance().init(app);
    }

    /**
     * @param view original view
     * @return
     */
    public static Builder from(View view) {
        return new Builder(view);
    }


    /**
     * if you use {@link Builder#toLazy(Class)},you need invoke this in next activity to start animation
     *
     * @param activity target activity
     * @param view     target view
     */
    public static void releaseLazy(Activity activity, View view) {
        TransitionManager.getInstance().executeLazyView(activity, view);
    }
}
