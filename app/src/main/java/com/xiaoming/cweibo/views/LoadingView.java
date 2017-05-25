package com.xiaoming.cweibo.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;
import com.xiaoming.cweibo.R;

/**
 * @author slience
 * @des
 * @time 2017/5/2315:14
 */

public class LoadingView {

    private RelativeLayout mRelativeLayout;
    private AVLoadingIndicatorView mAvi;
    private View view;
    private String mTag = "loadingTag";

    public LoadingView(RelativeLayout rlContent) {
        mRelativeLayout = rlContent;
        view = View.inflate(mRelativeLayout.getContext(), R.layout.layout_loading, null);
        //防止多个重复的view的增加
        view.setTag(mTag);
        if(mRelativeLayout.findViewWithTag(mTag)!=null){
            return;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        init(view);
        mRelativeLayout.addView(view, layoutParams);
        hide();
    }

    private void init(View v) {
        mAvi = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
    }

    public void show() {
        if (view != null) {
            mAvi.show();
        }
    }

    public void hide() {
        if (view != null) {
            mAvi.hide();
        }
    }

}
