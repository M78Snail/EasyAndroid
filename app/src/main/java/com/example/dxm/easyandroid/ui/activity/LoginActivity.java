package com.example.dxm.easyandroid.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.base.system.BaseActivity;
import com.example.dxm.easyandroid.bean.MyUser;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by duxiaoming on 16/8/8.
 *
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.fab_login)
    FloatingActionButton fab;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void initAfter() {
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_go, R.id.fab_login})
    public void onClick(View view) {
        Logger.d("登陆");
        switch (view.getId()) {
            case R.id.btn_go:
                Logger.d("登陆");
                final String user = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                BmobUser.loginByAccount(getApplicationContext(), user, password, new LogInListener<MyUser>() {

                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (myUser != null) {
                            Logger.i("登陆成功" + myUser.getUsername());
                            LoginToMain();
                        } else {
                            Logger.e(" 登录失败" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.fab_login:
                Logger.d("注册");
                regesiter();
                break;
        }
    }

    private void LoginToMain() {
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent i2 = new Intent(this, MainActivity.class);
        startActivity(i2, oc2.toBundle());
    }

    private void regesiter() {
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
