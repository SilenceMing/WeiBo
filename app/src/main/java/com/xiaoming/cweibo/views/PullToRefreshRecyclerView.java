package com.xiaoming.cweibo.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * @author slience
 * @des
 * @time 2017/5/2212:08
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    /**
     * 滚动方向
     * @return
     */
    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView  = new RecyclerView(context,attrs);
        recyclerView.setId(com.handmark.pulltorefresh.library.R.id.recyclerview);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        //判断是否有子item
        if(mRefreshableView.getChildCount()==0){
            return true;
        }
        int count = mRefreshableView.getChildCount();
        //获取可见视图的第一个子视图
        View view = mRefreshableView.getChildAt(count-1);
        //获取相应子视图的position
        int position = mRefreshableView.getChildLayoutPosition(view);
        if(position >= mRefreshableView.getAdapter().getItemCount()-1){
            //因为定义RecyclerView的分割线时，增加了像素的大小，所以判断时总是相差几个像素
            return view.getBottom() <= mRefreshableView.getBottom();
        }
        return false;
    }

    @Override
    protected boolean isReadyForPullStart() {
        //判断是否有子item
        if(mRefreshableView.getChildCount()==0){
            return true;
        }
        //获取可见视图的第一个子视图
        View view = mRefreshableView.getChildAt(0);
        //获取相应子视图的position
        int position = mRefreshableView.getChildLayoutPosition(view);
        //判断当前可见的第一个Item是否是RecyclerView中的第0个item
        if(position == 0){
            //如果页面的顶端布局和整个RecyclerView的布局顶端位置一致，则返回TRUE
                return view.getTop() == mRefreshableView.getTop();
        }
        return false;
    }
    public void setLayoutManager(RecyclerView.LayoutManager manager){
        mRefreshableView.setLayoutManager(manager);
    }
    public void addItemDecoration(RecyclerView.ItemDecoration decoration){
        mRefreshableView.addItemDecoration(decoration);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        mRefreshableView.setAdapter(adapter);
    }
}
