package com.example.dxm.easyandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.adapter.LeftMenuAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by duxiaoming on 16/8/1.
 */
public class LeftMenuFragment extends Fragment {
    private static final int SIZE_MENU_ITEM = 3;

    private MenuItem[] mItems = new MenuItem[SIZE_MENU_ITEM];

    private LeftMenuAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_leftmenu,null);
        initView();
        return rootView;
    }

    private void initView(){
        mRecyclerView= (RecyclerView) rootView.findViewById(R.id.id_listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        String[] left=new String[]{"知识体系","热门专题"," 项目常用框架","设置"};
        List<String> data= Arrays.asList(left);
        mAdapter=new LeftMenuAdapter(getContext(),data);
        mRecyclerView.setAdapter(mAdapter);
    }
    //选择回调的接口
    public interface OnMenuItemSelectedListener {
        void menuItemSelected(String title);
    }
    private OnMenuItemSelectedListener mMenuItemSelectedListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener menuItemSelectedListener) {
        this.mMenuItemSelectedListener = menuItemSelectedListener;
    }
}
