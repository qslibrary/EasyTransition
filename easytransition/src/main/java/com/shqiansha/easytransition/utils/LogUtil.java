package com.shqiansha.easytransition.utils;

import android.text.TextUtils;
import android.util.Log;

import com.shqiansha.easytransition.BuildConfig;


public class LogUtil {
    private static final String S="ssssssssssss";
    public static void i(String info){
        i(null,info);
    }
    public static void i(String tag,String info){
        if(BuildConfig.DEBUG) {
            if(!TextUtils.isEmpty(tag)&&!TextUtils.isEmpty(info)) info=tag+":"+info;
            if(info!=null) Log.i(S,info);
        }
    }
}
