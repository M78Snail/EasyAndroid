package com.example.dxm.easyandroid.ui.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.base.system.BaseActivity;
import com.example.dxm.easyandroid.bean.ArticleDetail;
import com.example.dxm.easyandroid.db.DatabaseHelper;
import com.example.dxm.easyandroid.listeners.DataListener;
import com.example.dxm.easyandroid.utils.HttpUtils;
import com.example.dxm.easyandroid.utils.WrapHtml;
import com.example.dxm.easyandroid.widget.ThumbUpView;


/**
 * Created by duxiaoming on 16/8/2.
 * blog:m78star.com
 */
public class ArticleDetailActivity extends BaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_articledetail;
    }

    @Override
    public void initAfter() {
        initView();
        getArticleDetail();
    }

    private String title;
    private String postId;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private ThumbUpView thumbUpView;

    private void initView() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        thumbUpView= (ThumbUpView) findViewById(R.id.thumbup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        postId = intent.getStringExtra("postId");
        collapsingToolbar.setTitle(title);

    }

    /**
     * 获取文章内容
     */
    private void getArticleDetail() {
        if (postId != null) {
            ArticleDetail articleDetail = DatabaseHelper.getInstance(getApplicationContext()).loadArticleDetail(postId);
            if (articleDetail != null)
                packHtml(articleDetail.getContent());
            else {
                HttpUtils.getInstance().findArticleDetail(ArticleDetailActivity.this, postId, new DataListener<ArticleDetail>() {

                    @Override
                    public void complete(ArticleDetail result) {
                        String content = result.getContent();
                        packHtml(content);
                        DatabaseHelper.getInstance(getApplicationContext()).saveArticleDetail(result);
                    }

                    @Override
                    public void fail() {

                    }
                });
            }
        }
    }

    /**
     * 包装加载html页面
     * @param content html内容
     */
    private void packHtml(String content) {
        mProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        mWebView = (WebView) findViewById(R.id.id_webview);
        String html = WrapHtml.getInstance().wrapHtml(title, content);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebSettings settings = mWebView.getSettings();
                settings.setBuiltInZoomControls(true);
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.loadDataWithBaseURL("", html, "text/html", "utf8", "404");

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
                onBackPressed();
                break;
        }
        return true;

    }
}
