package com.shqiansha.easytransition.factory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shqiansha.easytransition.MappingData;
import com.shqiansha.easytransition.core.Element;
import com.shqiansha.easytransition.core.Transition;
import com.shqiansha.easytransition.entity.Group;
import com.shqiansha.easytransition.transitions.CommonViewTransition;
import com.shqiansha.easytransition.transitions.ImageViewTransition;
import com.shqiansha.easytransition.transitions.TextViewTransition;
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
    }

    private void addTo(Group group, Class clzElement, Group.Params params) {
        group.getTo().setElement(ElementFactory.createElement(clzElement));
        if (params.toLazy) {
            group.getTo().setActivity(params.toActivity);
            group.getTo().setLazy(true);
        } else {
            group.getTo().setId(params.toId);
        }
    }

    private void addTransition(Group group, Group.Params params) {
        group.addGoTransition(TransitionFactory.createDefaultTransition(params.fromView));
        group.addBackTransition(TransitionFactory.createDefaultTransition(params.fromView));
        group.setTransitionDuration(params.duration);
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

    static class TransitionFactory {
        static Transition createDefaultTransition(View view) {
            if (view instanceof TextView) {
                return new TextViewTransition();
            } else if (view instanceof ImageView) {
                return new ImageViewTransition();
            } else {
                return new CommonViewTransition();
            }
        }
    }
}
