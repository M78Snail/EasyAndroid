package com.example.dxm.easyandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dxm.easyandroid.bean.ContentValue;
import com.example.dxm.easyandroid.utils.CreatListFragmentFactory;

/**
 * Created by duxiaoming on 16/8/20.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = ContentValue.NEWS;

    public NewsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public CharSequence getPageTitle(int position) {
        return this.TITLES[position];
    }

    public int getCount() {
        return this.TITLES.length;
    }

    public Fragment getItem(int position) {
        return CreatListFragmentFactory.getInstance().newFragment(1,position);
    }
}
