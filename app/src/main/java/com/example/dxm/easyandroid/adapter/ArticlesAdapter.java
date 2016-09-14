package com.example.dxm.easyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;
import com.example.dxm.easyandroid.bean.Article;
import com.example.dxm.easyandroid.bean.Article;
import com.example.dxm.easyandroid.ui.activity.ArticleDetailActivity;

import java.util.List;

/**
 * Created by dxm on 2016/7/8.
 */
public class ArticlesAdapter extends CommonAdapter<Article> {

    private OnItemClickJump onItemClickJump;
    private List<Article> data;

    public ArticlesAdapter(Context context, List<Article> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    public void setOnItemClickJump(OnItemClickJump onItemClickJump) {
        this.onItemClickJump = onItemClickJump;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }

    public List<Article> getData() {
        return data;
    }

    @Override
    public void convert(ViewHolder helper, final Article item, int position) {
        RelativeLayout relativeLayout = helper.getView(R.id.relative);
        TextView txtTitle = helper.getView(R.id.txt_title);
        TextPaint tp = txtTitle.getPaint();
        tp.setFakeBoldText(true);
        TextView txtSummary = helper.getView(R.id.txt_summary);
        txtTitle.setText(item.getTitle());
        txtSummary.setText(item.getSummary());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", item.getTitle());
                bundle.putString("postId", item.getPostId());
                onItemClickJump.clickToJump(bundle);

            }
        });

    }

    public interface OnItemClickJump {
        void clickToJump(Bundle bundle);
    }
}
