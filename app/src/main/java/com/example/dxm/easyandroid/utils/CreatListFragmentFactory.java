package com.example.dxm.easyandroid.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.dxm.easyandroid.ui.fragment.ListFragment;
import com.example.dxm.easyandroid.ui.fragment.NewsListFragment;

/**
 * Created by duxiaoming on 16/8/1.
 * blog:m78star.com
 */
public class CreatListFragmentFactory {

    private static CreatListFragmentFactory mInstance;

    private CreatListFragmentFactory() {
    }

    public static CreatListFragmentFactory getInstance() {
        if (mInstance == null) {
//            synchronized (CreatListFragmentFactory.class) {
                if (mInstance == null)
                    mInstance = new CreatListFragmentFactory();
//            }
        }
        return mInstance;
    }

    public Fragment newFragment(int type, int position) {

        Fragment fg = null;
        Bundle b;
        switch (type) {
            case 0:
                fg = new ListFragment();
                b = new Bundle();
                b.putInt("position", position);
                fg.setArguments(b);
                break;

            case 1:
                fg = new NewsListFragment();
                b = new Bundle();
                b.putInt("position", position);
                fg.setArguments(b);
                break;
        }
        return fg;

    }


}
