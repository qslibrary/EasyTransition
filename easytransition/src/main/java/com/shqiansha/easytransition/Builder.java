package com.shqiansha.easytransition;

import android.app.Activity;
import android.view.View;

import com.shqiansha.easytransition.core.Group;
import com.shqiansha.easytransition.factory.GroupFactory;
import com.shqiansha.easytransition.core.TransitionManager;
import com.shqiansha.easytransition.utils.ActivityUtils;

public class Builder {

    private Group.Params p;

    Builder(View view) {
        p = new Group.Params();
        p.fromView = view;
    }

    /**
     * animation time
     *
     * @param duration
     * @return
     */
    public Builder duration(long duration) {
        p.duration = duration;
        return this;
    }

    /**
     * disable back transition
     * @return
     */
    public Builder disableBack() {
        p.backTransition = false;
        return this;
    }

    /**
     * @param id of shared view in next
     */
    public void to(int id) {
        p.toId = id;
        build();
    }

    /**
     * use this will not start animation until you invoke {@link ET#releaseLazy(Activity, View)}
     *
     * @param activity the activity includes shared view
     */
    public void toLazy(Class<? extends Activity> activity) {
        p.toActivity = ActivityUtils.getName(activity);
        p.toLazy = true;
        build();
    }

    private void build() {
        Group group = GroupFactory.getInstance().create(p);
        TransitionManager.getInstance().addGroup(group);
    }


}
