package com.shqiansha.easytransition.factory;

import android.view.View;

import com.shqiansha.easytransition.config.MappingData;
import com.shqiansha.easytransition.core.Element;
import com.shqiansha.easytransition.core.Group;
import com.shqiansha.easytransition.utils.ActivityUtils;

import org.jetbrains.annotations.NotNull;

public class GroupFactory {
    private volatile static GroupFactory instance;

    @NotNull
    public static GroupFactory getInstance() {
        if (instance == null) {
            synchronized (GroupFactory.class) {
                if (instance == null) instance = new GroupFactory();
            }
        }
        return instance;
    }

    public Group create(Group.Params params) {
        for (Class<? extends View> clz : MappingData.keys()) {
            if (clz.isInstance(params.fromView)) {
                Group group = new Group();

                //添加element
                Class clzElement = MappingData.get(clz);
                addFrom(group, clzElement, params);
                addTo(group, clzElement, params);

                //添加动画
                addTransition(group, params);
                return group;
            }
        }
        return null;
    }

    private void addFrom(Group group, Class clzElement, Group.Params params) {
        group.getFrom().setElement(ElementFactory.createElement(clzElement));
        group.getFrom().setActivity(ActivityUtils.getLast());
        group.getFrom().saveValues(params.fromView);
        group.getFrom().saveSharedValues(params.fromView);
    }

    private void addTo(Group group, Class clzElement, Group.Params params) {
        group.getTo().setElement(ElementFactory.createElement(clzElement));
        group.getTo().saveSharedValues(params.fromView);
        if (params.toLazy) {
            group.getTo().setActivity(params.toActivity);
            group.getTo().setLazy(true);
        } else {
            group.getTo().setId(params.toId);
        }
    }

    private void addTransition(Group group, Group.Params params) {
        group.setGoTransitions(params.goTransitions);
        group.setBackTransitions(params.backTransitions);
        group.setTransitionDuration(params.duration);
        group.setBackTransition(params.back);
        group.updateViewValues(params.fromView, true);
    }

    static class ElementFactory {
        static Element createElement(Class<Element> elementClass) {
            try {
                return elementClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
