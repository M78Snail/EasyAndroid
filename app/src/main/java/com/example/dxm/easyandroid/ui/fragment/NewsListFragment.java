package com.example.dxm.easyandroid.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.adapter.NewsListAdapter;
import com.example.dxm.easyandroid.spider.NewsItem;
import com.example.dxm.easyandroid.ui.activity.NewsDetailActivity;
import com.example.dxm.easyandroid.utils.HttpUtils;
import com.example.dxm.easyandroid.widget.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duxiaoming on 16/8/20.
 * blog:m78star.com
 */
public class NewsListFragment extends BasePutToRefreshFragment<NewsListAdapter> {

    private int position;
    private NewsListAdapter adapter;

    private List<NewsItem> data=new ArrayList<>();

    private CircleProgressBar mCircleProgressBar;
    private View rootview;

    private int currentPage=1;

    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否已加载完毕
     */
    private boolean isLoadDataCompleted;

    @Override
    public NewsListAdapter getAdapter() {
        adapter=new NewsListAdapter(getContext(),data,R.layout.list_news_item);
        return adapter;
    }

    @Override
    public View getRootView() {
        rootview = View.inflate(getContext(), R.layout.fragment_review, null);
        return rootview;
    }

    @Override
    public void initAfter() {
        mCircleProgressBar = (CircleProgressBar) rootview.findViewById(R.id.circle_progressbar);
        adapter.setOnItemClickJump(new NewsListAdapter.OnItemClickJump() {
            @Override
            public void clickToJump(Bundle bundle) {
                Intent i=new Intent(getContext(), NewsDetailActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        isViewCreated = true;
    }

    @Override
    public void requestDataByNet(int actionType) {
    }

    /**
     * 第一次加载的时候
     */
    public void getArticleData() {
        mCircleProgressBar.setVisibility(View.VISIBLE);

        //data = DatabaseHelper.getInstance(getContext()).loadArticle(ContentValue.KINDS[position]);
        if (data.size()== 0) {
            new DownloadNewsTask().execute();
        }
        else{
//            adapter.setmDatas(data);
//            adapter.notifyDataSetChanged();
            mCircleProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Bundle bundle = getArguments();
        position = bundle.getInt("position");
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            isLoadDataCompleted=true;
            getArticleData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isLoadDataCompleted = true;
            getArticleData();
        }
    }

    

    private class DownloadNewsTask extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            data=HttpUtils.getInstance().findNews(position,currentPage);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setmMoveEnable(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapter.setmDatas(data);
//            Logger.i("data.size"+adapter.getData().size());
            adapter.notifyDataSetChanged();
            mCircleProgressBar.setVisibility(View.GONE);
            setmMoveEnable(true);
        }
    }
}
