package com.example.dxm.easyandroid.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by duxiaoming on 16/8/20.
 * http://blog.csdn.net/yanzi1225627/article/details/38308963 *
 * description: 处理NewItem的业务类
 */
public class NewsItemBiz {

    public List<NewsItem> getNewsItems(int newsType, int currentPage) throws CommonException, IOException {
        String urlStr = URLUtil.generateUrl(newsType, currentPage);
        String htmlStr = new DataUtil().doGet_ok(urlStr);
        List<NewsItem> newsItems = new ArrayList<>();
        NewsItem newsItem = null;

        Document doc = Jsoup.parse(htmlStr);

        Elements units = doc.getElementsByClass("unit");
        for (int i = 0; i < units.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setNewsType(newsType);

            Element unit_ele = units.get(i);
            Element h1_ele = unit_ele.getElementsByTag("h1").get(0);
            Element h1_a_ele = h1_ele.child(0);

            String title = h1_a_ele.text();
            String href = h1_a_ele.attr("href");

            newsItem.setTitle(title);
            newsItem.setLink(href);

            Element h4_ele = unit_ele.getElementsByTag("h4").get(0);
            Element ago_ele = h4_ele.getElementsByClass("ago").get(0);
            String date = ago_ele.text();

            newsItem.setDate(date);

            Element dl_ele = unit_ele.getElementsByTag("dl").get(0);
            Element dt_ele = dl_ele.child(0); //dt
            try {
                Element img_ele = dt_ele.child(0);
                String imgLink = img_ele.child(0).attr("src");
                newsItem.setImgLink(imgLink);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            Element content_ele = dl_ele.child(1); //dd
            String content = content_ele.text();
            newsItem.setContent(content);
            newsItems.add(newsItem);
        }
        return newsItems;
    }

//    /**
//     * 根据文章url返回一个NewsDto对象
//     *
//     * @param urlStr
//     * @return
//     * @throws CommonException
//     */
//    public NewsDto getNews(String urlStr) throws CommonException, IOException {
//
//        NewsDto newsDto = new NewsDto();
//
//        List<News> newses = new ArrayList<>();
//        String htmlStr=new DataUtil().doGet_ok(urlStr);
//        Logger.d("htmlStr"+htmlStr);
////        String htmlStr = DataUtil.doGet(urlStr);
//        Document doc = Jsoup.parse(htmlStr);
//
//        //获得文章第一个detail
//        if (doc.select(".wrapper").size() != 0) {
//            Element detailEle = doc.select(".wrapper").get(0);
//
//            //标题
//            Element titleEle = detailEle.select("h1").get(0);
//
//            News news = new News();
//            news.setTitle(titleEle.text());
//            news.setType(News.NewsType.TITLE);
//            newses.add(news);
//
//            //摘要
////            Element summaryEle = detailEle.select("div.summary").get(0);
////            news = new News();
////            news.setSummary(summaryEle.text());
////            news.setType(News.NewsType.SUMMARY);
////            newses.add(news);
//
//            //内容
//            Element contentEle = detailEle.select(".text").get(0);
//            Elements childrenEle = contentEle.children();
//            for (Element child : childrenEle) {
//                Elements imgEles = child.getElementsByTag("img");
//                if (imgEles.size() > 0) {
//                    for (Element imgEle : imgEles) {
//                        if (imgEle.attr("src").equals(""))
//                            continue;
//                        news = new News();
//                        news.setImageLink(imgEle.attr("src"));
//                        news.setType(News.NewsType.IMG);
//                        newses.add(news);
//                    }
//                }
//                imgEles.remove();
//
//                if (child.text().equals(""))
//                    continue;
//                news = new News();
//                news.setType(News.NewsType.CONTENT);
//                try {
//
//                    if (child.children().size() == 1) {
//                        Element cc = child.child(0);
//                        if (cc.tagName().equals("b")) {
//                            news.setType(News.NewsType.BOLD_TITLE);
//                        }
//                    }
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }
//                Logger.v("child.outerHtml()"+child.outerHtml());
//                news.setContent(child.outerHtml());
////                news.setContent("000000000000000000000000");
//                newses.add(news);
//            }
//            newsDto.setNewses(newses);
//
//        }
//        return newsDto;
//
//    }

    public NewsDto getNews_ok(String urlStr) throws CommonException, IOException {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<News>();
        String htmlStr =new DataUtil().doGet_ok(urlStr);
        Document doc = Jsoup.parse(htmlStr);

        // 获得文章中的第一个detail
        Element detailEle = doc.select(".left .detail").get(0);
        // 标题
        Element titleEle = detailEle.select("h1.title").get(0);
        News news = new News();
        news.setTitle(titleEle.text());
        news.setType(News.NewsType.TITLE);
        newses.add(news);
        // 摘要
        //Element summaryEle = detailEle.select("div.summary").get(0);
        //news = new News();
        //news.setSummary(summaryEle.text());
        //newses.add(news);
        // 内容
        Element contentEle = detailEle.select("div.con.news_content").get(0);
        Elements childrenEle = contentEle.children();

        for (Element child : childrenEle)
        {
            Elements imgEles = child.getElementsByTag("img");
            // 图片
            if (imgEles.size() > 0)
            {
                for (Element imgEle : imgEles)
                {
                    if (imgEle.attr("src").equals(""))
                        continue;
                    news = new News();
                    news.setImageLink(imgEle.attr("src"));
                    newses.add(news);
                }
            }
            // 移除图片
            imgEles.remove();

            if (child.text().equals(""))
                continue;

            news = new News();
            news.setType(News.NewsType.CONTENT);

            try
            {
                if(child.children().size()==1)
                {
                    Element cc = child.child(0);
                    if(cc.tagName().equals("b"))
                    {
                        news.setType(News.NewsType.BOLD_TITLE);
                    }
                }

            } catch (IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            news.setContent(child.outerHtml());
            newses.add(news);
        }
        newsDto.setNewses(newses);
        return newsDto;
    }
}
