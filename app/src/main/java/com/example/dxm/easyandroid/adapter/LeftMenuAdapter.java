package com.example.dxm.easyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dxm.easyandroid.R;

import java.util.List;

/**
 * Created by duxiaoming on 16/8/1.
 */
public class LeftMenuAdapter extends RecyclerView.Adapter<MyHolder> {


    private List<String> mData;
    private LayoutInflater mInflater;
    private MyItemClickListener listener;


    //private int[] icon=new int[]{R.mipmap.icon_memory_on,R.mipmap.icon_appearance_on,R.mipmap.icon_http_on,R.mipmap.icon_other_on};

    public LeftMenuAdapter(Context context, List<String> mDatas) {
        this.mData = mDatas;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_leftmenu, parent, false);
        MyHolder myHolder = new MyHolder(view, listener);
        return myHolder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setMyItemClickListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv.setText(mData.get(position));
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

}

class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private LeftMenuAdapter.MyItemClickListener listener;

    TextView tv;

    public MyHolder(View itemView, LeftMenuAdapter.MyItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
        tv = (TextView) itemView.findViewById(R.id.txt_title);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(view, getPosition());
        }
    }
}
