package com.example.dxm.easyandroid.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by duxiaoming on 16/8/3.
 */
public class ArticleDetail extends BmobObject implements Serializable {
    private String content;
    private String postId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
