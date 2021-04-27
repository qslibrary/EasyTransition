package com.shqiansha.easytransition;

import android.app.Activity;
import android.transition.Transition;
import android.view.View;

import com.shqiansha.easytransition.core.Group;
import com.shqiansha.easytransition.factory.GroupFactory;
import com.shqiansha.easytransition.core.TransitionManager;
import com.shqiansha.easytransition.factory.TransitionFactory;
import com.shqiansha.easytransition.utils.ActivityUtils;

import java.util.List;

public class Builder {

    private Group.Params p;

    Builder(View view) {
        p = new Group.Params();
        p.fromView = view;
        p.goTransitions = TransitionFactory.createDefaultTransition(view);
        p.backTransitions = TransitionFactory.createDefaultTransition(view);
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
     *
     * @return
     */
    public Builder disableBack() {
        p.back = false;
        return this;
    }

    public Builder addGoTransition(Transition transition) {
        p.goTransitions.add(transition);
        return this;
    }

    public Builder addBackTransition(Transition transition) {
        p.backTransitions.add(transition);
        return this;
    }

    public Builder replaceGoTransitions(List<Transition> transitions) {
        p.goTransitions = transitions;
        return this;
    }

    public Builder replaceGoTransition(Transition transition) {
        p.goTransitions.clear();
        p.goTransitions.add(transition);
        return this;
    }

    public Builder replaceBackTransition(Transition transition) {
        p.backTransitions.clear();
        p.backTransitions.add(transition);
        return this;
    }

    public Builder replaceBackTransitions(List<Transition> transitions) {
        p.backTransitions = transitions;
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
