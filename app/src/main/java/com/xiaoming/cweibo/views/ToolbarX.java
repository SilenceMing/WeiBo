package com.xiaoming.cweibo.views;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * @author Slience_Manager
 * @time 2017/5/17 11:15
 */

public class ToolbarX {
    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionBar;

    public ToolbarX(Toolbar toolbar, AppCompatActivity activity) {
        mToolbar = toolbar;
        mActivity = activity;
        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

    }
    public ToolbarX setTitle(String title){
        mActionBar.setTitle(title);
        return this;
    }
    public ToolbarX setSubTitle(String subTitle){
        mActionBar.setSubtitle(subTitle);
        return this;
    }
    public ToolbarX setTitle(int resId){
        mActionBar.setTitle(resId);
        return this;
    }
    public ToolbarX setSubTitle(int resId){
        mActionBar.setSubtitle(resId);
        return this;
    }
    public ToolbarX setNavigationOnClickListener(View.OnClickListener listener){
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }
    public ToolbarX setNavigationIcon(int resId){
        mToolbar.setNavigationIcon(resId);
        return this;
    }
    public ToolbarX setNavigationIcon(Drawable icon){
        mToolbar.setNavigationIcon(icon);
        return this;
    }
    public ToolbarX setDisplayHomeAsUpEnabled(boolean show){
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }
    public ToolbarX hide(){
        mActionBar.hide();
        return this;
    }


}
