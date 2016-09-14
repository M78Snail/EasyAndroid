package com.example.dxm.easyandroid.ui.activity;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.base.system.BaseActivity;
import com.example.dxm.easyandroid.ui.fragment.ArticleKindFragment;
import com.example.dxm.easyandroid.ui.fragment.NewsFragment;
import com.example.dxm.easyandroid.utils.DoubleClickExitHelper;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.update.BmobUpdateAgent;


public class MainActivity extends BaseActivity {
    /**
     * 两次退出
     */
    private DoubleClickExitHelper mDoubleClickExit;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private NavigationView mNavigationView;

    private DrawerLayout mDrawerLayout;
    protected Toolbar mToolbar;

    private Fragment mCurrentFragment;
    private ArticleKindFragment mArticleKindFragment;
    private NewsFragment mNewsFragment;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;

    private FragmentManager fm;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initAfter() {
        initView();
        initToolBar();
    }

    /**
     * 当需要使用Tool的时候调此来初始化
     */
    public void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (mToolbar != null) {
            mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            mToolbar.setTitle("知识体系");// 标题的文字需在setSupportActionBar之前，不然会无效
            setSupportActionBar(mToolbar);
            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_update:
                                BmobUpdateAgent.forceUpdate(MainActivity.this);
                                break;
                            case R.id.nav_user:

                                BmobUser user = BmobUser.getCurrentUser(getApplicationContext());
                                if (user == null) {
                                    Logger.d("user为空,进行登陆跳转");
                                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(i);
                                } else {
                                    Logger.d("user不是空" + user.getUsername());
                                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(i);
                                }
                                break;
                            case R.id.nav_messages:
                                //行内资讯
//                                Logger.i("行内资讯");
                                if (mNewsFragment == null) {
                                    mNewsFragment = new NewsFragment(getSupportFragmentManager());
                                }
                                switchContent(mNewsFragment);
                                mToolbar.setTitle("行内咨询");
                                break;
                            case R.id.nav_home:
                                if (mArticleKindFragment == null) {
                                    mArticleKindFragment = new ArticleKindFragment(fm);
                                }
                                switchContent(mArticleKindFragment);
                                mToolbar.setTitle("知识体系");
                                break;
                        }

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        mDoubleClickExit = new DoubleClickExitHelper(this);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        setupDrawerContent(mNavigationView);
        initFragment();

    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        if (mCurrentFragment == null) {
            mArticleKindFragment = new ArticleKindFragment(fm);
            mNewsFragment=new NewsFragment(fm);
            mCurrentFragment = mArticleKindFragment;
            fm.beginTransaction().replace(R.id.id_content_container, mCurrentFragment).commit();
        }
    }

    private void switchContent(Fragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(mCurrentFragment).add(R.id.id_content_container, to).commit();
            } else {
                transaction.hide(mCurrentFragment).show(to).commit();
            }
            mCurrentFragment = to;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


}
