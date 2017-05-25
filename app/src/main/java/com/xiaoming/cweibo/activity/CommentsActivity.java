package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
 * @des
 * @time 2017/5/2122:21
 */

public class CommentsActivity extends BaseActivity {
    private EditText et_comments;
    private long id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra(ParameterKey.ID,0);
        init();
    }

    private void init() {
        et_comments = (EditText) findViewById(R.id.et_content);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repost,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Post(et_comments.getText().toString());
        return super.onOptionsItemSelected(item);
    }

    private void Post(final String content) {
        new BaseNetWork(this, Urls.CREATE) {
            @Override
            public WeiboParameters onPrepare() {
                WeiboParameters weiboParameters = new WeiboParameters(Constant.APP_KEY);
                weiboParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(getApplicationContext()).getToken());
                weiboParameters.put(ParameterKey.COMMENT, content);
                weiboParameters.put(ParameterKey.ID,id);
                return weiboParameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                EventBus.getDefault().post("COMMENTS_FINISH");
                finish();
            }
        }.post();
    }
}
