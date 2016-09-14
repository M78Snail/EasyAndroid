package com.example.dxm.easyandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.adapter.ArticlesAdapter;
import com.example.dxm.easyandroid.bean.Article;
import com.example.dxm.easyandroid.bean.ContentValue;
import com.example.dxm.easyandroid.db.DatabaseHelper;
import com.example.dxm.easyandroid.listeners.DataListener;
import com.example.dxm.easyandroid.ui.activity.ArticleDetailActivity;
import com.example.dxm.easyandroid.utils.HttpUtils;
import com.example.dxm.easyandroid.widget.CircleProgressBar;

import java.util.List;


/**
 * Created by dxm on 2016/7/11
 */
public class ListFragment extends BasePutToRefreshFragment<ArticlesAdapter> {

    private int position;
    private ArticlesAdapter adapter;

    private List<Article> data;

    private CircleProgressBar mCircleProgressBar;
    private View rootview;

    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否已加载完毕
     */
    private boolean isLoadDataCompleted;


    @Override
    public ArticlesAdapter getAdapter() {
        adapter = new ArticlesAdapter(getContext(), data, R.layout.list_knowledge_item);
        return adapter;
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
    public View getRootView() {
        rootview = View.inflate(getContext(), R.layout.fragment_review, null);
        return rootview;
    }

    /**
     * 第一次加载的时候
     */
    public void getArticleData() {
        mCircleProgressBar.setVisibility(View.VISIBLE);

        data = DatabaseHelper.getInstance(getContext()).loadArticle(ContentValue.KINDS[position]);

        if (data.size()== 0) {
            HttpUtils.getInstance().findArticles(getContext(), position, new DataListener<List<Article>>() {
                @Override
                public void complete(List<Article> result) {
                    mCircleProgressBar.setVisibility(View.GONE);
                    adapter.setmDatas(result);
                    adapter.notifyDataSetChanged();
                    DatabaseHelper.getInstance(getContext()).saveArticles(result);
                }

                @Override
                public void fail() {
                    mCircleProgressBar.setVisibility(View.GONE);
                }
            });
        }
        else{
            adapter.setmDatas(data);
            adapter.notifyDataSetChanged();
            mCircleProgressBar.setVisibility(View.GONE);
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

    @Override
    public void initAfter() {
        initView();
        isViewCreated = true;

    }

    private void initView() {
        mCircleProgressBar = (CircleProgressBar) rootview.findViewById(R.id.circle_progressbar);
        adapter.setOnItemClickJump(new ArticlesAdapter.OnItemClickJump() {
            @Override
            public void clickToJump(Bundle bundle) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void requestDataByNet(int actionType) {
        if (actionType == REFRESH_TYPE_PULL) {
            HttpUtils.getInstance().findArticles(getContext(), position, new DataListener<List<Article>>() {

                @Override
                public void complete(List<Article> result) {
                    adapter.setmDatas(result);
                    adapter.notifyDataSetChanged();
                    mRefreshLayout.setRefreshing(false);
                    DatabaseHelper.getInstance(getContext()).saveArticles(result);
                }

                @Override
                public void fail() {
                    mRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            HttpUtils.getInstance().findArticles(getContext(), position, new DataListener<List<Article>>() {

                @Override
                public void complete(List<Article> result) {
                    adapter.setmDatas(result);
                    adapter.notifyDataSetChanged();
                    mRefreshLayout.setLoading(false);
                    DatabaseHelper.getInstance(getContext()).saveArticles(result);

                }

                @Override
                public void fail() {
                    mRefreshLayout.setLoading(false);
                }
            });
        }
    }


}
