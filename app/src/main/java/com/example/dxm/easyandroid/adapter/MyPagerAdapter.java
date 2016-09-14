package com.example.dxm.easyandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dxm.easyandroid.bean.ContentValue;
import com.example.dxm.easyandroid.utils.CreatListFragmentFactory;

/**
 * Created by duxiaoming on 16/8/1.
 * blog:m78star.com
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = ContentValue.KINDS;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CharSequence getPageTitle(int position) {
        return this.TITLES[position];
    }

    public int getCount() {
        return this.TITLES.length;
    }

    public Fragment getItem(int position) {
        return CreatListFragmentFactory.getInstance().newFragment(0,position);
    }
}
