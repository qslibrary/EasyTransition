package com.shqiansha.easytransition.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shqiansha.easytransition.config.MappingData;
import com.shqiansha.easytransition.elements.ImageViewElement;
import com.shqiansha.easytransition.elements.TextViewElement;
import com.shqiansha.easytransition.utils.ActivityUtils;
import com.shqiansha.easytransition.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TransitionManager {
    private volatile static TransitionManager instance;
    private Application app;
    private List<Group> groups = new ArrayList<>();


    @NotNull
    public static TransitionManager getInstance() {
        if (instance == null) {
            synchronized (TransitionManager.class) {
                if (instance == null) instance = new TransitionManager();
            }
        }
        return instance;
    }

    public void init(Application app) {
        this.app = app;
        registerListener();
        registerElements();
    }

    private void registerListener() {
        ActivityUtils.clear();
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                ActivityUtils.put(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                List<Group> matched = getMatchedGroups(activity);
                if (matched.size() > 0) perform(matched, activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                if (activity.isFinishing()) {
                    for (Group group : groups) {
                        if (group.isTargetActivity(activity) && group.isAnimating()) {
                            group.cancelAnimations();
                        }
                    }
                }
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                ActivityUtils.pop(activity);
                for (int i = groups.size() - 1; i >= 0; i--) {
                    if (groups.get(i).getFrom().isActivityEqual(activity)) groups.remove(i);
                }
            }
        });
    }

    /**
     * register default elements
     */
    private void registerElements() {
        MappingData.register(TextView.class, TextViewElement.class);
        MappingData.register(ImageView.class, ImageViewElement.class);
    }

    /**
     * get matched groups that will perform transition in the activity
     *
     * @param activity
     * @return
     */
    public List<Group> getMatchedGroups(Activity activity) {
        List<Group> list = new ArrayList<>();
        for (Group group : groups) {
            if (group.isTargetActivity(activity)) list.add(group);
        }
        return list;
    }

    /**
     * perform transition including go and back
     *
     * @param matchedGroups
     * @param activity
     */

    private void perform(List<Group> matchedGroups, Activity activity) {
        for (Group group : matchedGroups) {
            group.execute(activity);
            if (group.isBack()) groups.remove(group);
        }
    }

    /**
     * execute animations lazily
     *
     * @param activity
     * @param view
     */
    public void executeLazyView(Activity activity, View view) {
        List<Group> matched = getMatchedGroups(activity);
        if (matched.size() > 0) {
            for (Group group : matched) {
                if (group.isWaitingToAnim()) {
                    group.getTo().saveValues(view);
                    group.startGoTransition(activity.getWindow(), view);
                }
            }
        }
    }

    /**
     * add group,it will skip if exist
     *
     * @param group
     */
    public void addGroup(Group group) {
        for (Group old : groups) {
            boolean exist = StringUtils.isEqual(old.getFrom().getActivity(), group.getFrom().getActivity())
                    && old.getFrom().getId() == group.getFrom().getId()
                    && old.getTo().getId() == group.getTo().getId();
            if (exist) return;
        }
        groups.add(group);
    }
}
