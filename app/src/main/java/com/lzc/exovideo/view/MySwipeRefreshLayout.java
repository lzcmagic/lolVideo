package com.lzc.exovideo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private int lastInterceptX;
    private int lastInterceptY;
    private int lastMoveX;
    private int lastMoveY;
    public MySwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (super.onInterceptTouchEvent(ev)){
            return true;
        }
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int x= (int) ev.getRawX();
        int y= (int) ev.getRawY();
        boolean intercept=false;
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:
                intercept=false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltx=x-lastInterceptX;
                int delty=y-lastInterceptY;
                if (Math.abs(deltx)>Math.abs(delty)){
                    intercept=false;
                }else{
                    intercept=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept=false;
                break;
            default:break;
        }

        lastInterceptX=x;
        lastInterceptY=y;
        lastMoveX=x;
        lastMoveY=y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
                default:break;
        }
        return super.onTouchEvent(ev);
    }
}
