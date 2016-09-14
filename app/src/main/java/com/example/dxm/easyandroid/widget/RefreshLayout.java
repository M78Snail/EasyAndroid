package com.example.dxm.easyandroid.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import com.example.dxm.easyandroid.R;

/**
 * Created by dxm on 2016/7/5.
 * http://www.lai18.com/content/2533578.html
 */
public class RefreshLayout extends SwipeRefreshLayout {

    // listview实例
    private ListView mListView;
    // 上拉接口监听器, 到了最底部的上拉加载操作
    private OnLoadListener mOnLoadListener;
    // 是否在加载中 ( 上拉加载更多 )
    private boolean isLoading = false;

    // ListView的加载中footer
    private View mListViewFooter;

    // 按下时的y坐标
    private int mYDown;
    // 抬起时的y坐标
    private int mLastY;
    // 滑动到最下面时的上拉操作
    private int mTouchSlop;


    public RefreshLayout(Context context) {
        this(context,null);
    }
    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //一个圆形进度条

        mListViewFooter = LayoutInflater.from(context).inflate(
                R.layout.listview_footer, null, false);
        mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
        Log.e("mTouchSlop",mTouchSlop+"");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }
    // 获取ListView对象
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;

            }
        }
    }

    // 设置加载状态,添加或者移除加载更多圆形进度条
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListView.addFooterView(mListViewFooter);
        } else {
            mListView.removeFooterView(mListViewFooter);

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                Log.d("TAG.LONG>>","("+(mYDown - mLastY));
                Log.d("LastY",mLastY+"");
                // 抬起
                if ((mYDown - mLastY) >= mTouchSlop && isLoading == false&&mLastY!=0) {
                    // 设置状态
                    setLoading(true);
                    //
                    mOnLoadListener.onLoad();
                }
                mLastY=0;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    //设置监听器
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }



    // 加载更多的接口
    public interface OnLoadListener {
        void onLoad();
    }
}
