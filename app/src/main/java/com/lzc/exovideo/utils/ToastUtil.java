package com.lzc.exovideo.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.lzc.exovideo.MyApp;

public class ToastUtil {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void toastShort( String text) {

        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(MyApp.getAppContext(), text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, 200);

        mToast.show();
    }

    public static void showToast(int resId) {
        toastShort(MyApp.getAppContext().getResources().getString(resId));
    }

}
