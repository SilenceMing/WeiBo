package com.xiaoming.cweibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.R;

/**
 * @author Slience_Manager
 * @time 2017/5/17 14:26
 */

public class LanddingPageActivity extends BaseActivity {
    private SsoHandler mSsoHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        WbSdk.install(this,new AuthInfo(LanddingPageActivity.this, Constant.APP_KEY, Constant.REDIRECT_URL, Constant.SCOPE));
        mSsoHandler = new SsoHandler(LanddingPageActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断是否已经登录
                checkLogin();
            }
        },500);
    }

    private void checkLogin() {

        if(AccessTokenKeeper.readAccessToken(getApplicationContext()).isSessionValid()){
            startActivity(new Intent(LanddingPageActivity.this,HomePageActivity.class));
            finish();
        }else{
            mSsoHandler.authorize(new WbAuthListener() {
                @Override
                public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                    AccessTokenKeeper.writeAccessToken(getApplicationContext(),oauth2AccessToken);
                    startActivity(new Intent(LanddingPageActivity.this,HomePageActivity.class));
                    finish();
                }

                @Override
                public void cancel() {
                    Toast.makeText(LanddingPageActivity.this, "取消授权", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                    Toast.makeText(LanddingPageActivity.this, wbConnectErrorMessage.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_landding;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }

}
