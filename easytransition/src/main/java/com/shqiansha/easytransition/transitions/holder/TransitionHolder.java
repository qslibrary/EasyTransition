package com.shqiansha.easytransition.transitions.holder;

import android.animation.Animator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

public class TransitionHolder<T extends Transition> {
    private T transition;
    private TransitionValues startValues;
    private TransitionValues endValues;

    public TransitionHolder() {
    }

    public TransitionHolder(T transition) {
        this.transition = transition;
    }

    public void setTransition(T transition) {
        this.transition = transition;
    }

//    public void captureViewValues(View view, boolean start) {
//
//        if (start) {
//            if (startValues == null) {
//                startValues = new TransitionValues(view);
//            } else {
//                startValues.view = view;
//            }
//            transition.captureStartValues(startValues);
////            startValues.view = null;
//        } else {
//            if (endValues == null) {
//                endValues = new TransitionValues(view);
//            } else {
//                endValues.view = view;
//            }
//            transition.captureEndValues(endValues);
////            endValues.view = null;
//        }
//    }

    public void captureStartViewValues(View view) {
        if (startValues == null) {
            startValues = new TransitionValues(view);
        } else {
            startValues.view = view;
        }
        transition.captureStartValues(startValues);
    }

    public void captureEndViewValues(View view) {
        if (endValues == null) {
            endValues = new TransitionValues(view);
        } else {
            endValues.view = view;
        }
        transition.captureEndValues(endValues);
    }


    public Animator getAnimator(ViewGroup decor,String transitionViewTag,boolean start) {
//        if(start){
            if(endValues!=null){
                View view=decor.findViewWithTag(transitionViewTag);
                if(view!=null) endValues.view=view;
            }
            return transition.createAnimator(decor, startValues, endValues);
//        }else{
//            if(startValues!=null){
//                View view=decor.findViewWithTag(transitionViewTag);
//                if(view!=null) startValues.view=view;
//            }
//            return transition.createAnimator(decor, endValues, startValues);
//        }
    }
}
