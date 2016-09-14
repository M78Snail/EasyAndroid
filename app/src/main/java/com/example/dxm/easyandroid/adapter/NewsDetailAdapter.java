package com.example.dxm.easyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.spider.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duxiaoming on 16/8/22.
 *
 */
public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.ItemHolder> {

    private List<News> mData;

    private Context mContext;

    public NewsDetailAdapter(Context context) {
        this.mData = new ArrayList<>();
        this.mContext = context;
    }

    //开放数据接口,让使用到adapter的地方可以操作mData
    public List<News> addData(List<News> data) {
        this.mData = data;
        return mData;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHolder holder = null;
        View view;
        switch (viewType) {
            case News.NewsType.TITLE:
                view = View.inflate(mContext, R.layout.news_content_title_item, null);
                holder = new ItemHolder(view, News.NewsType.TITLE);
                break;
            case News.NewsType.CONTENT:
                view = View.inflate(mContext, R.layout.news_content_item, null);
                holder = new ItemHolder(view, News.NewsType.CONTENT);
                break;
            case News.NewsType.IMG:
                view = View.inflate(mContext, R.layout.news_content_img_item, null);
                holder = new ItemHolder(view, News.NewsType.IMG);
                break;
            case News.NewsType.BOLD_TITLE:
                view = View.inflate(mContext, R.layout.news_content_bold_title_item, null);
                holder = new ItemHolder(view, News.NewsType.BOLD_TITLE);
                break;
            case News.NewsType.SUMMARY:
                view = View.inflate(mContext, R.layout.news_content_summary_item, null);
                holder = new ItemHolder(view, News.NewsType.SUMMARY);
                break;

        }
        return holder;
    }

    private String content;

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        int type = holder.mType;
        if(type== News.NewsType.BOLD_TITLE||type== News.NewsType.CONTENT) {
            if(mData.get(position).getContent()!=null)
            content = replaceBlank(String.valueOf(Html.fromHtml(mData.get(position).getContent())));
        }
        switch (type) {
            case News.NewsType.TITLE:
                holder.mContent.setText(mData.get(position).getTitle());
                break;
            case News.NewsType.CONTENT:
                holder.mContent.setText("\u3000\u3000" + content);
                break;
            case News.NewsType.BOLD_TITLE:
                holder.mContent.setText("\u3000\u3000"+content);
                break;
            case News.NewsType.SUMMARY:
                holder.mContent.setText("");
                break;
            case News.NewsType.IMG:
                Picasso.with(mContext).load(mData.get(position).getImageLink()).into(holder.mIcon);
                break;

        }


    }
    public  String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private TextView mContent;
        private ImageView mIcon;
        private int mType;

        public ItemHolder(View rootview, int viewType) {
            super(rootview);
            switch (viewType) {
                case News.NewsType.TITLE:
                    mType = News.NewsType.TITLE;
                    mContent = (TextView) rootview.findViewById(R.id.text);
                    break;
                case News.NewsType.CONTENT:
                    mType = News.NewsType.CONTENT;
                    mContent = (TextView) rootview.findViewById(R.id.text);
                    break;
                case News.NewsType.BOLD_TITLE:
                    mContent = (TextView) rootview.findViewById(R.id.text);
                    mType = News.NewsType.BOLD_TITLE;
                    break;
                case News.NewsType.SUMMARY:
                    mContent = (TextView) rootview.findViewById(R.id.text);
                    mType = News.NewsType.CONTENT;
                    break;
                case News.NewsType.IMG:
                    mIcon = (ImageView) rootview.findViewById(R.id.imageView);
                    mType = News.NewsType.IMG;
                    break;
            }
        }
    }
}
