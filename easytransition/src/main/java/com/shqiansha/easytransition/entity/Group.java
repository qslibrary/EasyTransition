package com.shqiansha.easytransition.entity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.shqiansha.easytransition.utils.ActivityUtils;
import com.shqiansha.easytransition.core.Transition;

import java.util.ArrayList;
import java.util.List;

public class Group<T extends View> {

    private static final int BEHAVIOR_GO = 1;
    private static final int BEHAVIOR_BACK = 2;

    private static final int STATE_NONE = 0;
    private static final int STATE_WAITING_TO_ANIM = 1;
    private static final int STATE_ANIMATING = 2;

    private int behavior = BEHAVIOR_GO;
    private int state = STATE_NONE;

    private ElementHolder<T> from = new ElementHolder<>();
    private ElementHolder<T> to = new ElementHolder<>();

    private List<Transition<T>> goTransitions = new ArrayList<>();
    private List<Transition<T>> backTransitions = new ArrayList<>();
    private AnimatorSet mSet;

    private long transitionDuration = 1000;
    private String transitionViewTag = "";

    public void saveOriginalValues(Activity activity) {
        if (isGo()) {
            T view = activity.getWindow().getDecorView().findViewById(from.getId());
            from.saveValues(view);
        } else {
            T view = activity.getWindow().getDecorView().findViewById(to.getId());
            to.saveValues(view);
        }
    }

    public void execute(Activity activity) {
        if (isGo()) {
            executeGo(activity);
        } else if (isBack()) {
            executeBack(activity);
        }
    }

    private void executeGo(Activity activity) {
        Window window = activity.getWindow();
        window.getAttributes().windowAnimations = 0;
        FrameLayout decor = (FrameLayout) window.getDecorView();
        hideContent(decor);

        //创建过渡视图
        final T view = from.getTransitionView(decor);
        if (view.getParent() == null) decor.addView(view);
        String tag = System.currentTimeMillis() + "";
        view.setTag(tag);
        transitionViewTag = tag;

        state = STATE_WAITING_TO_ANIM;
        if (!to.isLazy()) {
            final T toView = decor.findViewById(to.getId());
            startGoTransition(decor, toView);
        }
    }

    public void startGoTransition(FrameLayout container, final T toView) {
        final T view = container.findViewWithTag(transitionViewTag);
        toView.post(new Runnable() {
            @Override
            public void run() {
                to.saveValues(toView);
                playGoAnimations(view);
            }
        });
    }

    public void executeBack(Activity activity) {
        Window window = activity.getWindow();
        FrameLayout decor = (FrameLayout) window.getDecorView();
        hideContent(decor);
        final T view = to.getTransitionView(decor);
        if (view.getParent() == null) decor.addView(view);
        final T fromView = decor.findViewById(from.getId());
        fromView.post(new Runnable() {
            @Override
            public void run() {
                playBackAnimations(view);
            }
        });
    }

    private void playGoAnimations(final T view) {
        List<Animator> animations = new ArrayList<>();
        for (Transition<T> tran : goTransitions) {
            animations.addAll(tran.onCreate(from.getValues(), to.getValues(), (T) view));
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animations);
        set.setDuration(transitionDuration);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = STATE_ANIMATING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                FrameLayout decor = (FrameLayout) view.getParent();
                decor.removeView(view);
                showContent(decor);
                behavior = BEHAVIOR_BACK;
                mSet = null;
                state = STATE_NONE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                to.saveValues(view);
                behavior = BEHAVIOR_BACK;
                state = STATE_NONE;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
        mSet = set;
    }

    private void playBackAnimations(final T view) {
        List<Animator> animations = new ArrayList<>();
        for (Transition<T> tran : backTransitions) {
            animations.addAll(tran.onCreate(to.getValues(), from.getValues(), view));
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animations);
        set.setDuration(transitionDuration);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = STATE_ANIMATING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                FrameLayout decor = (FrameLayout) view.getParent();
                decor.removeView(view);
                showContent(decor);
                behavior = BEHAVIOR_GO;
                mSet = null;
                state = STATE_NONE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                behavior = BEHAVIOR_BACK;
                state = STATE_NONE;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
        mSet = set;
    }

    public void cancelAnimations() {
        if (mSet != null) mSet.cancel();
    }

    public boolean isAnimating() {
        return mSet != null && state == STATE_ANIMATING;
    }

    public boolean isWaitingToAnim() {
        return state == STATE_WAITING_TO_ANIM;
    }

    private void hideContent(FrameLayout decor) {
        for (int i = 0; i < decor.getChildCount(); i++) {
            decor.getChildAt(i).setVisibility(View.INVISIBLE);
        }
    }

    private void showContent(FrameLayout decor) {
        for (int i = 0; i < decor.getChildCount(); i++) {
            decor.getChildAt(i).setVisibility(View.VISIBLE);
        }
    }

    public ElementHolder<T> getFrom() {
        return from;
    }

    public ElementHolder<T> getTo() {
        return to;
    }

    public boolean isGo() {
        return behavior == BEHAVIOR_GO;
    }

    public boolean isBack() {
        return behavior == BEHAVIOR_BACK;
    }

    public void addGoTransition(Transition<T> transition) {
        goTransitions.add(transition);
    }

    public void addBackTransition(Transition<T> transition) {
        backTransitions.add(transition);
    }

    public void setTransitionDuration(long transitionDuration) {
        this.transitionDuration = transitionDuration;
    }

    public boolean isTargetActivity(Activity activity) {
        //跳转
        if (isGo() && ActivityUtils.isPrevious(from.getActivity())) {
            if (to.isActivityEqual(activity)) return true;
            if (activity.getWindow().getDecorView().findViewById(to.getId()) != null) return true;
        }
        //返回
        if (isBack() && ActivityUtils.isLast(to.getActivity())) {
            if (from.isActivityEqual(activity)) return true;
        }

        return false;
    }

    public boolean isOriginalActivity(Activity activity) {
        return isGo() && from.isActivityEqual(activity) || isBack() && to.isActivityEqual(activity);
    }

    public static class Params{
        public View fromView;
        public int toId;
        public String toActivity;
        public boolean toLazy;
        public long duration=1000;
    }

}
