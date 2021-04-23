package com.shqiansha.easytransition;

import android.text.TextUtils;
import android.util.Log;


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
