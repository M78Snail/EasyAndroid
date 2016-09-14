package com.example.dxm.easyandroid.spider;

/**
 * Created by duxiaoming on 16/8/20.
 * blog:m78star.com
 * 新闻属性的bean
 */
public class NewsItem {
    private int id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章链接
     */
    private String link;

    /**
     * 发布日期
     */
    private String date;

    /**
     * 图片链接
     */
    private String imgLink;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章类型
     */
    private int newsType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    @Override
    public String toString() {
        return "NewsItem [id=" + id + ", title=" + title + ", link=" + link + ", date=" + date + ", imgLink=" + imgLink
                + ", content=" + content + ", newsType=" + newsType + "]";
    }
}
