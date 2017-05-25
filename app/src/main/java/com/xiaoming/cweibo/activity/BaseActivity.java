package com.xiaoming.cweibo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.views.LoadingView;
import com.xiaoming.cweibo.views.MVP_view.BaseView;
import com.xiaoming.cweibo.views.ToolbarX;


/**
 * @author Slience_Manager
 * @time 2017/5/16 23:43
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ToolbarX mToolbarX;
    private Toolbar mToolbar;
    private RelativeLayout mRlContent;
    private LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baselayout);
        initView();
        View view = getLayoutInflater().inflate(getLayoutId(), mRlContent, false);
        mRlContent.addView(view);
        mToolbarX = new ToolbarX(mToolbar, this);
        mLoadingView = new LoadingView(mRlContent);
    }

    public abstract int getLayoutId();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right, R.anim.anim_out_left_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
    }

    public ToolbarX getToolbar() {
        if (null == mToolbarX) {
            mToolbarX = new ToolbarX(mToolbar, this);
        }
        return mToolbarX;
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRlContent = (RelativeLayout) findViewById(R.id.ll_content);
    }
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView() {
        mLoadingView.show();
    }

    @Override
    public void hideLoadingView() {
        mLoadingView.hide();
    }
}
