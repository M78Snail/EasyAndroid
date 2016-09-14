package com.example.dxm.easyandroid.base.system;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dxm on 2016/7/4.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(getLayoutResource());
        initAfter();
    }


    /**
     * 设置布局文件
     *
     * @return 布局文件
     */
    public abstract int getLayoutResource();

    /**
     * 加载完布局文件之后
     */
    public abstract void initAfter();


}

