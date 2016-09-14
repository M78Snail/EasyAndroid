package com.example.dxm.easyandroid.utils;

import com.example.dxm.easyandroid.spider.CommonException;
import com.example.dxm.easyandroid.spider.Constant;
import com.example.dxm.easyandroid.spider.NewsItem;
import com.example.dxm.easyandroid.spider.NewsItemBiz;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by duxiaoming on 16/8/20.
 */
public class Test {
    public Test() {

    }

    public void test(){
        try {
            NewsItemBiz newsItemBiz = new NewsItemBiz();
            int currentPage = 1;
            Logger.d("-----------业界-----------");
            List<NewsItem> newsItems = newsItemBiz.getNewsItems(Constant.NEWS_TYPE_YEJIE, currentPage);
            for (NewsItem item : newsItems) {
                Logger.d(item.toString());
            }


        }catch (CommonException e){
            Logger.e("错误"+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
