package com.example.dxm.easyandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.spider.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duxiaoming on 16/8/22.
 *
 */
public class NewsContentAdapter extends BaseAdapter {
    private List<News> mDatas = new ArrayList<News>();

    private Context mContext;

    public NewsContentAdapter(Context context) {
        this.mContext = context;
    }

    public void addList(List<News> datas) {
        mDatas.addAll(datas);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {

        switch (mDatas.get(position).getType()) {
            case News.NewsType.TITLE:
                return 0;
            case News.NewsType.SUMMARY:
                return 1;
            case News.NewsType.CONTENT:
                return 2;
            case News.NewsType.IMG:
                return 3;
            case News.NewsType.BOLD_TITLE:
                return 4;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = mDatas.get(position); // 获取当前项数据
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            switch (news.getType()) {
                case News.NewsType.TITLE:
                    convertView = View.inflate(mContext, R.layout.news_content_title_item, null);
                    holder.mTextView = (TextView) convertView.findViewById(R.id.text);
                    break;
                case News.NewsType.SUMMARY:
                    convertView = View.inflate(mContext, R.layout.news_content_summary_item, null);
                    holder.mTextView = (TextView) convertView.findViewById(R.id.text);
                    break;
                case News.NewsType.CONTENT:
                    convertView = View.inflate(mContext, R.layout.news_content_item, null);
                    holder.mTextView = (TextView) convertView.findViewById(R.id.text);
                    break;
                case News.NewsType.IMG:
                    convertView = View.inflate(mContext, R.layout.news_content_img_item, null);
                    holder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
                    break;
                case News.NewsType.BOLD_TITLE:
                    convertView = View.inflate(mContext, R.layout.news_content_bold_title_item, null);
                    holder.mTextView = (TextView) convertView.findViewById(R.id.text);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        switch (news.getType()) {
            case News.NewsType.IMG:
                Picasso.with(mContext).load(news.getImageLink()).into(holder.mImageView);
                break;
            case News.NewsType.TITLE:
                holder.mTextView.setText(news.getTitle());
                break;
            case News.NewsType.SUMMARY:
                holder.mTextView.setText(news.getSummary());
                break;
            case News.NewsType.CONTENT:
                holder.mTextView.setText("\u3000\u3000" + Html.fromHtml(news.getContent()));
                break;
            case News.NewsType.BOLD_TITLE:
                holder.mTextView.setText("\u3000\u3000" + Html.fromHtml(news.getContent()));
            default:

                // holder.mTextView.setText(Html.fromHtml(item.getContent(),
                // null, new MyTagHandler()));
                // holder.content.setText(Html.fromHtml("<ul><bold>加粗</bold>sdfsdf<ul>",
                // null, new MyTagHandler()));
                break;
        }

        return convertView;
    }

    private final class ViewHolder {
        TextView mTextView;
        ImageView mImageView;
    }
}
