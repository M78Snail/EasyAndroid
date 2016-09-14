package com.example.dxm.easyandroid.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.adapter.NewsDetailAdapter;
import com.example.dxm.easyandroid.base.system.BaseActivity;
import com.example.dxm.easyandroid.spider.CommonException;
import com.example.dxm.easyandroid.spider.News;
import com.example.dxm.easyandroid.spider.NewsDto;
import com.example.dxm.easyandroid.spider.NewsItemBiz;
import com.example.dxm.easyandroid.widget.CircleProgressBar;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duxiaoming on 16/8/22.
 */
public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.loading_progressbar)
    CircleProgressBar mLoadingProgressbar;
    @BindView(R.id.recycle_content)
    RecyclerView mListContent;
    @BindView(R.id.backdrop)
    ImageView mIcon;
    @BindView(R.id.txt_summary)
    TextView txtSummary;

    private NewsDetailAdapter adapter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_newsdetail;
    }

    private String link;
    private List<News> mData;

    @Override
    public void initAfter() {
        ButterKnife.bind(this);
        adapter = new NewsDetailAdapter(getApplicationContext());
        mListContent.setAdapter(adapter);
        initView();

        new LoadDataTask().execute();
    }

    private void initView() {
        mListContent.setNestedScrollingEnabled(false);
        mListContent.setLayoutManager(new LinearLayoutManager(this));
//        mListContent.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL_LIST));
        mListContent.setAdapter(adapter);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("link");
        String icon = bundle.getString("icon");
        Picasso.with(getApplicationContext()).load(icon).into(mIcon);
        String summary = bundle.getString("summary");
        txtSummary.setText(summary);
        String title = bundle.getString("title");
        collapsingToolbar.setTitle(title);
    }

    private class LoadDataTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            NewsItemBiz biz = new NewsItemBiz();
            try {
                NewsDto newsDto=biz.getNews_ok(link);
//                NewsDto newsDto = biz.getNews(link);
                mData = newsDto.getNewses();

            } catch (CommonException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mData == null) {
                return;
            }
            adapter.addData(mData);
            adapter.notifyDataSetChanged();
            mLoadingProgressbar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mLoadingProgressbar.setVisibility(View.VISIBLE);
        }
    }
}
