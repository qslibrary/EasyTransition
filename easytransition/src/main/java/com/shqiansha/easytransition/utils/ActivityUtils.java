package com.shqiansha.easytransition.utils;

import android.app.Activity;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {
    private static List<String> activities = new ArrayList<>();

    @Nullable
    public static String getLast() {
        if (activities.size() > 0) return activities.get(activities.size() - 1);
        else return null;
    }

    @Nullable
    public static String getPrevious() {
        if (activities.size() > 1) return activities.get(activities.size() - 2);
        else return null;
    }

    public static void put(Activity activity) {
        activities.add(activity.getClass().getName());
    }

    public static void pop(Activity activity) {
        String name=getName(activity);
        for(int i=activities.size()-1;i>=0;i--){
            if(activities.get(i).equals(name)){
                activities.remove(i);
                break;
            }
        }
    }

    public static void clear() {
        activities.clear();
    }

    public static boolean isPrevious(Activity activity) {
        return isPrevious(activity.getClass().getName());
    }

    public static boolean isPrevious(String activityName) {
        String pre = getPrevious();
        if (pre == null) return false;
        return pre.equals(activityName);
    }

    public static boolean isLast(Activity activity){
        return isLast(activity.getClass().getName());
    }

    public static boolean isLast(String activityName) {
        String pre = getLast();
        if (pre == null) return false;
        return pre.equals(activityName);
    }

    public static String getName(Activity activity){
        return activity.getClass().getName();
    }
    public static String getName(Class<? extends Activity> clz){
        return clz.getName();
    }


}
