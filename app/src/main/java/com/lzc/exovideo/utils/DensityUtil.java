package com.lzc.exovideo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DensityUtil {


    public static int dip2px(int value,Context context){
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
        return (int) v;
    }

    public static int getScreentWidthPX(Activity activity){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreentHeightPX(Activity activity){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
