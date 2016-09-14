package com.example.dxm.easyandroid.spider;

import java.util.List;

/**
 * Created by duxiaoming on 16/8/22.
 */
public class NewsDto {
    private List<News> newses;
    private String nextPagerUrl;

    public List<News> getNewses() {
        return newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }

    public String getNextPagerUrl() {
        return nextPagerUrl;
    }

    public void setNextPagerUrl(String nextPagerUrl) {
        this.nextPagerUrl = nextPagerUrl;
    }
}
