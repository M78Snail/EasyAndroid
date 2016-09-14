package com.example.dxm.easyandroid.utils;

import android.content.Context;

import com.example.dxm.easyandroid.bean.Article;
import com.example.dxm.easyandroid.bean.ArticleDetail;
import com.example.dxm.easyandroid.bean.ContentValue;
import com.example.dxm.easyandroid.listeners.DataListener;
import com.example.dxm.easyandroid.spider.CommonException;
import com.example.dxm.easyandroid.spider.NewsItem;
import com.example.dxm.easyandroid.spider.NewsItemBiz;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by duxiaoming on 16/8/1.
 * blog:m78star.com
 */
public class HttpUtils {
    private final String[] kind = ContentValue.KINDS;

    private static HttpUtils mInstance;

    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null)
                    mInstance = new HttpUtils();
            }
        }
        return mInstance;
    }


    /**
     * 通过种类寻找知识体系列表
     *
     * @param context  context
     * @param position 种类编号
     * @param position 种类编号
     */
    public <T> void findArticles(final Context context, int position, final DataListener<T> listener) {
        BmobQuery<Article> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("kind", kind[position]);
        bmobQuery.findObjects(context, new FindListener<Article>() {
            @Override
            public void onSuccess(List<Article> list) {
                listener.complete((T) list);
            }

            @Override
            public void onError(int i, String s) {
                listener.fail();
            }

        });
    }

    /**
     * 通过种类寻找知识体系列表
     *
     * @param context context
     * @param postId  文章编号
     */
    public <T> void findArticleDetail(final Context context, String postId, final DataListener<T> listener) {
        BmobQuery<ArticleDetail> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("postId", postId);
        bmobQuery.getObject(context, postId, new GetListener<ArticleDetail>() {
            @Override
            public void onSuccess(ArticleDetail articleDetail) {
                listener.complete((T) articleDetail);
            }

            @Override
            public void onFailure(int i, String s) {
                listener.fail();
            }
        });
    }

    public List<NewsItem> findNews(int type, int page) {
        List<NewsItem> mData = null;
        try {
            NewsItemBiz newsItemBiz = new NewsItemBiz();

            mData = newsItemBiz.getNewsItems(type, page);

        } catch (CommonException e) {
            Logger.e("错误" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mData;

    }

}
