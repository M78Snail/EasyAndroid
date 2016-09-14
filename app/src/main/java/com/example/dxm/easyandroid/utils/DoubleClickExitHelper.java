package com.example.dxm.easyandroid.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.base.system.AppManager;

/**
 * Created by Du.xiaoming on 2016/7/4.
 * blog:m78Star.com
 * Description:双击退出
 */
public class DoubleClickExitHelper {
    private final Activity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;
    public DoubleClickExitHelper(Activity activity){
        mActivity=activity;
        mHandler=new Handler(Looper.getMainLooper());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode!=KeyEvent.KEYCODE_BACK){
            return false;
        }
        if(isOnKeyBacking){
            mHandler.removeCallbacks(onBackTimeRunnable);
            if(mBackToast!=null){
                mBackToast.cancel();
            }
            AppManager.getAppManager().AppExit(mActivity);
            return true;
        }else{
            isOnKeyBacking=true;
            if(mBackToast == null) {
                mBackToast = Toast.makeText(mActivity, R.string.tip_double_click_exit, Toast.LENGTH_SHORT);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }
    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if(mBackToast != null){
                mBackToast.cancel();
            }
        }
    };

}
