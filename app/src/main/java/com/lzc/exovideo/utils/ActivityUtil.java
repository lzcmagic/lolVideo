package com.lzc.exovideo.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtil {

    public static void addFragment (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment,
                                              int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void replaceFragment (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment,
                                              int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId,fragment);
        transaction.commit();
    }

    public static void replaceFragmentWithTag (@NonNull FragmentManager fragmentManager,
                                        @NonNull Fragment fragment,
                                        int frameId,
                                        String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId,fragment,tag);
        transaction.commit();
    }
}
