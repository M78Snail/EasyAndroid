package com.example.dxm.easyandroid.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by duxiaoming on 16/8/3.
 */
public class Article extends BmobObject implements Serializable {
    private String postId;
    private String kind;
    private String author;
    private String title;
    private String summary;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
