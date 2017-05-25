package com.xiaoming.cweibo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/259:56
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    private List<T> mList;
    private final int layoutId;
    private Context mContext;

    public CommonAdapter(List<T> list, int layoutId, Context context) {
        mList = list;
        this.layoutId = layoutId;
        mContext = context;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder,mList.get(position));
    }

    public abstract void convert(CommonViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
