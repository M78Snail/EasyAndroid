package com.example.dxm.easyandroid.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.spider.NewsItem;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by duxiaoming on 16/8/21.
 */
public class NewsListAdapter extends CommonAdapter<NewsItem> {

    private OnItemClickJump onItemClickJump;
    private List<NewsItem> data;

    public NewsListAdapter(Context context, List<NewsItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, final NewsItem item, int position) {
        RelativeLayout relativeLayout = helper.getView(R.id.relative);
        TextView txtTitle = helper.getView(R.id.txt_title);
        TextPaint tp = txtTitle.getPaint();
        tp.setFakeBoldText(true);
        TextView txtSummary = helper.getView(R.id.txt_summary);
        txtTitle.setText(item.getTitle());
        txtSummary.setText(item.getContent());
        TextView txtDate = helper.getView(R.id.txt_date);
        txtDate.setText(item.getDate());

        ImageView img_icon = helper.getView(R.id.img_icon);
        String imgLink = item.getImgLink();
        if (imgLink != null) {
            img_icon.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(imgLink).into(img_icon);
        }
        else{
            img_icon.setVisibility(View.GONE);
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Logger.d("url"+item.getLink());
                bundle.putString("link", item.getLink());
                bundle.putString("summary",item.getContent());
                bundle.putString("icon",item.getImgLink());
                bundle.putString("title",item.getTitle());
                onItemClickJump.clickToJump(bundle);

            }
        });
    }

    public void setOnItemClickJump(OnItemClickJump onItemClickJump) {
        this.onItemClickJump = onItemClickJump;
    }

    public void setData(List<NewsItem> data) {
        this.data = data;
    }

    public List<NewsItem> getData() {
        return data;
    }

    public interface OnItemClickJump {
        void clickToJump(Bundle bundle);
    }
}
