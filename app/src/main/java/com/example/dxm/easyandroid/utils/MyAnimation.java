package com.example.dxm.easyandroid.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;

/**
 * Created by dxm on 2016/7/11.
 */
public class MyAnimation {
    private static MyAnimation mInstance;

    private MyAnimation(){
    }

    public static MyAnimation getInstance(){
        if(mInstance==null){
            synchronized (MyAnimation.class){
                if(mInstance==null){
                    mInstance = new MyAnimation();
                }
            }
        }
        return mInstance;
    }

    /**
     * 原地旋转
     * @param context context
     * @param view
     * @return
     */
    public Animation FlipAnimation(final Context context, final TextView view){
        Animation a = AnimationUtils.loadAnimation(context,
                R.animator.mi_laucher_scale_in);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(AnimationUtils
                        .loadAnimation(context,
                                R.animator.mi_laucher_scale_out));
            }
        });
        return a;
    }
}
