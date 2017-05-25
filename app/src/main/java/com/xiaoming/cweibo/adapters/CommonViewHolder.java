package com.xiaoming.cweibo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoming.cweibo.utils.CircleTransform;

/**
 * @author slience
 * @des
 * @time 2017/5/2419:31
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;
    private View convertView;
    private Context mContext;

    public CommonViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        convertView = itemView;
        mContext = itemView.getContext();
    }
    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if(view == null){
            view = convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }
    public void setText(int resId,String text){
        TextView view = getView(resId);
        view.setText(text);
    }
    public void setImageByUrl(int resId,String url){
        ImageView img = getView(resId);
        Glide.with(mContext).load(url).asBitmap().transform(new CircleTransform(mContext)).into(img);
    }
}
