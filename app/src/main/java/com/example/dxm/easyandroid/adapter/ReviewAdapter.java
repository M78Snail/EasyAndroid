package com.example.dxm.easyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;

import java.util.List;

/**
 * Created by dxm on 2016/7/6.
 */
public class ReviewAdapter extends BaseAdapter {

    private Context mContext;
    private List mContent;
    public ReviewAdapter(Context context, List content){
        this.mContent=content;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return mContent.size();
    }

    @Override
    public Object getItem(int i) {
        return mContent.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.mTextView = (TextView) convertView
                    .findViewById(R.id.tv_tips);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(mContent.get(i)+"");
        return convertView;
    }
    static class ViewHolder{
        TextView mTextView;
    }
}
