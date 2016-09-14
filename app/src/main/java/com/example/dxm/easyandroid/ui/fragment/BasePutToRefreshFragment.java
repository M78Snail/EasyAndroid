package com.example.dxm.easyandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.widget.RefreshLayout;

/**
 * Created by dxm on 2016/7/5.
 * Descrition:继承此类的布局文件swipe_refresh_layout，refresh_listview，两个名字不可以变
 */
public abstract class BasePutToRefreshFragment<T extends BaseAdapter> extends Fragment implements RefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    private View rootView;
    protected RefreshLayout mRefreshLayout;
    protected ListView listView;
    protected T adapter;

    //下拉刷新
    public static final int REFRESH_TYPE_PULL = 0;
    //上拉加载更多
    public static final int REFRESH_TYPE_LOAD_MORE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getRootView();
        initView();
        initAfter();
        return rootView;
    }

    private void initView() {
        mRefreshLayout = (RefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadListener(this);
        listView = (ListView) rootView.findViewById(R.id.refresh_listview);
        adapter = getAdapter();
        listView.setAdapter(adapter);

    }


    public abstract T getAdapter();

    @Override
    public void onRefresh() {

        if (mMoveEnabla) {
            mRefreshLayout.setLoading(false);
            requestDataByNet(REFRESH_TYPE_PULL);
            adapter.notifyDataSetChanged();
        }
        else {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoad() {

        if (mMoveEnabla) {
            mRefreshLayout.setRefreshing(false);
            requestDataByNet(REFRESH_TYPE_LOAD_MORE);
            adapter.notifyDataSetChanged();
        }
        else {
            mRefreshLayout.setLoading(false);

        }
    }

    private boolean mMoveEnabla = true;

    void setmMoveEnable(boolean a) {
        this.mMoveEnabla = a;
    }

    /**
     * 设置布局文件
     *
     * @return 布局文件
     */
    public abstract View getRootView();

    /**
     * 加载完布局文件之后
     */
    public abstract void initAfter();

    public abstract void requestDataByNet(int actionType);
}
