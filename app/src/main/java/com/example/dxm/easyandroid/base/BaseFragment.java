package com.example.dxm.easyandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dxm.easyandroid.R;

/**
 * Created by dxm on 2016/7/6.
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_knowledge,null);
        return rootView;
    }

    @Override
    public void onResume() {
        initAfter();
        super.onResume();
    }

    /**
     * 设置布局文件
     * @return 布局文件
     */
    public abstract int getLayoutResource();

    /**
     * 加载完布局文件之后
     */
    public abstract void initAfter();

}
