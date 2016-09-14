package com.example.dxm.easyandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dxm on 2016/7/7.
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;

    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mPosition = position;

        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context     上下文Activity
     * @param convertView 复用View
     * @param parent      祖View
     * @param layoutId    布局文件
     * @param position    位置
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId ViewID
     * @param text   字符
     */
    public void setText(int viewId, String text) {
        TextView view = getView(viewId);


        view.setText(text);

    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId     ViewID
     * @param drawableId 图片ID
     */
    public void setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId     ViewID
     * @param drawableId 图片ID
     */
    public void setDrawableResource(int viewId, Drawable drawableId) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawableId);

    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId ViewId
     * @param bm     图片
     */
    public void setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId ViewId
     * @param url    图片路径
     */
    public void setImageByUrl(int viewId, String url) {
//        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));
    }

    public int getPosition() {
        return mPosition;
    }

    public View getConvertView() {
        return mConvertView;
    }
}