package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.networks.BaseNetWork;
import com.xiaoming.cweibo.networks.Urls;

import org.greenrobot.eventbus.EventBus;

/**
 * @author slience
 * @des 转发微博
 * @time 2017/5/2120:13
 */

public class RepostActivity extends BaseActivity {
    private EditText mEtContent;
    private Long id;
    private ImageView mIvContent;
    private TextView mTvHead;
    private TextView mTvContent;
    private String tvHead;
    private String content;
    private String img_url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_repost);
        content = getIntent().getStringExtra(ParameterKey.STATUS);
        id = getIntent().getLongExtra(ParameterKey.ID,0);
        tvHead = getIntent().getStringExtra("HEAD_TITLE");
        img_url = getIntent().getStringExtra("IMG_URL");
        init();
    }

    private void init() {
        mEtContent = (EditText) findViewById(R.id.etContent);
        mIvContent = (ImageView) findViewById(R.id.iv_content);
        mTvHead = (TextView) findViewById(R.id.tv_head);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        Glide.with(getApplicationContext()).load(img_url).placeholder(R.mipmap.ic_default_header).into(mIvContent);
        mTvHead.setText("@"+tvHead);
        mTvContent.setText(content);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repost;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repost,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Post(mEtContent.getText().toString());
        return super.onOptionsItemSelected(item);
    }

    private void Post(final String content) {
        new BaseNetWork(this, Urls.REPOST) {
            @Override
            public WeiboParameters onPrepare() {
                WeiboParameters weiboParameters = new WeiboParameters(Constant.APP_KEY);
                weiboParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(getApplicationContext()).getToken());
                weiboParameters.put(ParameterKey.STATUS,content);
                weiboParameters.put(ParameterKey.ID,id);
                return weiboParameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                EventBus.getDefault().post("REPOST_FINISH");
                finish();
            }
        }.post();

    }
}
