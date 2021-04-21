package com.shqiansha.easytransition;

import android.app.Activity;
import android.view.View;

import com.shqiansha.easytransition.entity.Group;
import com.shqiansha.easytransition.factory.GroupFactory;
import com.shqiansha.easytransition.utils.ActivityUtils;

public class Builder {

    private Group.Params p;

    Builder(View view) {
        p = new Group.Params();
        p.fromView = view;
    }

    public Builder duration(long duration) {
        p.duration = duration;
        return this;
    }

    public void to(int id) {
        p.toId = id;
        build();
    }

    public void toLazy(Class<? extends Activity> activity) {
        p.toActivity = ActivityUtils.getName(activity);
        p.toLazy = true;
        build();
    }

    private void build() {
        Group group = GroupFactory.getInstance().create(p);
//        if(toLazy){
//            group=GroupFactory.getInstance().createLazily(from,toActivity);
//        }else{
//            group = GroupFactory.getInstance().create(from, toId);
//        }
        TransitionManager.getInstance().addGroup(group);
    }


}
