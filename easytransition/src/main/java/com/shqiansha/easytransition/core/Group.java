package com.shqiansha.easytransition.core;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.shqiansha.easytransition.elements.holder.ElementHolder;
import com.shqiansha.easytransition.utils.ActivityUtils;
import com.shqiansha.easytransition.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class Group<T extends View> {

    private static final int BEHAVIOR_GO = 1;
    private static final int BEHAVIOR_BACK = 2;

    private static final int STATE_NONE = 0;
    private static final int STATE_WAITING_TO_ANIM = 1;
    private static final int STATE_ANIMATING = 2;

    private int mBehavior = BEHAVIOR_GO;
    private int mState = STATE_NONE;

    private ElementHolder<T> mFrom = new ElementHolder<>();
    private ElementHolder<T> mTo = new ElementHolder<>();

    private List<Transition<T>> mGoTransitions = new ArrayList<>();
    private List<Transition<T>> mBackTransitions = new ArrayList<>();
    private AnimatorSet mSet;

    private long mTransitionDuration = 0;
    private String mTransitionViewTag = "";
    private int mOldWindowAnimations;
    private boolean mBackTransition;

    public void saveOriginalValues(Activity activity) {
        if (isGo()) {
            T view = activity.getWindow().getDecorView().findViewById(mFrom.getId());
            mFrom.saveValues(view);
        } else {
            T view = activity.getWindow().getDecorView().findViewById(mTo.getId());
            mTo.saveValues(view);
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
        mOldWindowAnimations = window.getAttributes().windowAnimations;
        window.getAttributes().windowAnimations = 0;
        FrameLayout decor = (FrameLayout) window.getDecorView();
        hideContent(decor);

        //创建过渡视图
        final T view = mFrom.getTransitionView(decor);
        if (view.getParent() == null) {
            decor.addView(view, ViewUtils.generateParams(mFrom.getValues()));
        }
        String tag = System.currentTimeMillis() + "";
        view.setTag(tag);
        mTransitionViewTag = tag;

        mState = STATE_WAITING_TO_ANIM;
        if (!mTo.isLazy()) {
            final T toView = decor.findViewById(mTo.getId());
            startGoTransition(window, toView);
        }
    }

    public void startGoTransition(final Window window, final T toView) {
        final T view = window.getDecorView().findViewWithTag(mTransitionViewTag);
        toView.post(new Runnable() {
            @Override
            public void run() {
                mTo.saveValues(toView);
                playGoAnimations(view, window);
            }
        });
    }

    public void executeBack(Activity activity) {
        if (mBackTransition) {
            Window window = activity.getWindow();
            FrameLayout decor = (FrameLayout) window.getDecorView();
            hideContent(decor);
            final T view = mTo.getTransitionView(decor);
            if (view.getParent() == null) {
                decor.addView(view, ViewUtils.generateParams(mTo.getValues()));
            }
            final T fromView = decor.findViewById(mFrom.getId());
            fromView.post(new Runnable() {
                @Override
                public void run() {
                    playBackAnimations(view);
                }
            });
        }
    }

    private void playGoAnimations(final T view, final Window window) {
        List<Animator> animations = new ArrayList<>();
        for (Transition<T> tran : mGoTransitions) {
            animations.addAll(tran.onCreate(mFrom.getValues(), mTo.getValues(), view));
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animations);
        set.setDuration(mTransitionDuration);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mState = STATE_ANIMATING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mState == STATE_ANIMATING) {
                    FrameLayout decor = (FrameLayout) view.getParent();
                    decor.removeView(view);
                    showContent(decor);
                    mBehavior = BEHAVIOR_BACK;
                    mSet = null;
                    mState = STATE_NONE;
                    if (!mBackTransition) window.setWindowAnimations(mOldWindowAnimations);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mTo.saveValues(view);
                mBehavior = BEHAVIOR_BACK;
                mState = STATE_NONE;
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
        for (Transition<T> tran : mBackTransitions) {
            animations.addAll(tran.onCreate(mTo.getValues(), mFrom.getValues(), view));
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animations);
        set.setDuration(mTransitionDuration);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mState = STATE_ANIMATING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mState == STATE_ANIMATING) {
                    FrameLayout decor = (FrameLayout) view.getParent();
                    decor.removeView(view);
                    showContent(decor);
                    mBehavior = BEHAVIOR_GO;
                    mSet = null;
                    mState = STATE_NONE;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mBehavior = BEHAVIOR_BACK;
                mState = STATE_NONE;
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
        mState = STATE_NONE;
    }

    public boolean isAnimating() {
        return mSet != null && mState == STATE_ANIMATING;
    }

    public boolean isWaitingToAnim() {
        return mState == STATE_WAITING_TO_ANIM;
    }

    private void hideContent(FrameLayout decor) {
        for (int i = 0; i < decor.getChildCount(); i++) {
            View view = decor.getChildAt(i);
            //过滤statusBarBackground
            if (view.getId() == android.R.id.statusBarBackground) break;

            decor.getChildAt(i).setVisibility(View.INVISIBLE);
        }
    }

    private void showContent(FrameLayout decor) {
        for (int i = 0; i < decor.getChildCount(); i++) {
            decor.getChildAt(i).setVisibility(View.VISIBLE);
        }
    }

    public ElementHolder<T> getFrom() {
        return mFrom;
    }

    public ElementHolder<T> getTo() {
        return mTo;
    }

    public boolean isGo() {
        return mBehavior == BEHAVIOR_GO;
    }

    public boolean isBack() {
        return mBehavior == BEHAVIOR_BACK;
    }

    public void addGoTransition(Transition<T> transition) {
        mGoTransitions.add(transition);
    }

    public void addBackTransition(Transition<T> transition) {
        mBackTransitions.add(transition);
    }

    public void setTransitionDuration(long mTransitionDuration) {
        this.mTransitionDuration = mTransitionDuration;
    }

    public void setBackTransition(boolean backTransition) {
        this.mBackTransition = backTransition;
    }

    public boolean isTargetActivity(Activity activity) {
        //跳转
        if (isGo() && ActivityUtils.isPrevious(mFrom.getActivity())) {
            if (mTo.isActivityEqual(activity)) return true;
            if (activity.getWindow().getDecorView().findViewById(mTo.getId()) != null) return true;
        }
        //返回
        if (isBack() && ActivityUtils.isLast(mTo.getActivity())) {
            if (mFrom.isActivityEqual(activity)) return true;
        }

        return false;
    }

    public boolean isOriginalActivity(Activity activity) {
        return isGo() && mFrom.isActivityEqual(activity) || isBack() && mTo.isActivityEqual(activity);
    }

    public static class Params {
        public View fromView;
        public int toId;
        public String toActivity;
        public boolean toLazy;
        public long duration = 500;
        public boolean backTransition = true;
    }

}
