package com.shqiansha.easytransition.factory;

import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shqiansha.easytransition.transitions.LocationTransition;
import com.shqiansha.easytransition.transitions.TextViewTransition;

import java.util.ArrayList;
import java.util.List;

public class TransitionFactory {
    public static List<Transition> createDefaultTransition(View view) {
        List<Transition> transitions = new ArrayList<>();
        if (view instanceof TextView) {
            transitions.add(new TextViewTransition());
        } else if (view instanceof ImageView) {
            transitions.add(new LocationTransition());
        } else {
            transitions.add(new LocationTransition());
        }
        return transitions;
    }
}
