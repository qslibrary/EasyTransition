package com.shqiansha.easytransition;

import android.app.Activity;
import android.app.Application;
import android.view.View;

public class ET {
    public static void init(Application app){
        TransitionManager.getInstance().init(app);
    }

    public static Builder from(View view){
        return new Builder(view);
    }

    public static void releaseLazy(Activity activity,View view){
        TransitionManager.getInstance().executeLazyView(activity,view);
    }
}
