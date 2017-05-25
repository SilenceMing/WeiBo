package com.xiaoming.cweibo.networks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.views.MVP_view.BaseView;

/**
 * @author Slience_Manager
 * @time 2017/5/19 9:57
 */

public abstract class BaseNetWork {
    private AsyncWeiboRunner mAsyncWeiboRunner;
    private String url;
    private BaseView mBaseView;
    private boolean mShowLoading;
    public BaseNetWork(BaseView baseView, String url) {
        mBaseView = baseView;
        mAsyncWeiboRunner = new AsyncWeiboRunner(mBaseView.getActivity());
        this.url = url;
    }
    public BaseNetWork(BaseView baseView, String url,boolean showLoading) {
        mBaseView = baseView;
        mAsyncWeiboRunner = new AsyncWeiboRunner(mBaseView.getActivity());
        this.url = url;
        mShowLoading = showLoading;
    }

    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            if(mShowLoading){
                mBaseView.hideLoadingView();
            }
            HttpResponse response = new HttpResponse();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(s);
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("error_code")) {
                    response.code = object.get("error_code").getAsInt();
                } else if (object.has("error")) {
                    response.message = object.get("error").getAsString();
                    mBaseView.onError(response.message);
                    onFinish(response);
                    return;
                } else if (object.has("statuses")) {
                    response.response = object.get("statuses").toString();
                } else if (object.has("users")) {
                    response.response = object.get("users").toString();
                } else if (object.has("comments")) {
                    response.response = object.get("comments").toString();
                }else if(object.has("favorites")){
                    response.response = object.get("favorites").toString();
                } else {
                    response.response = s;
                }
            }
            onFinish(response);
        }

        @Override
        public void onWeiboException(WeiboException e) {
            if(mShowLoading){
                mBaseView.hideLoadingView();
            }
            HttpResponse response = new HttpResponse();
            response.message = e.getMessage();
            mBaseView.onError(response.message);
            onFinish(response);
        }
    };

    public void get() {
        request("GET");
    }

    public void post() {
        request("POST");
    }

    public void delete() {
        request("DELETE");
    }

    private void request(String method){
        if(mShowLoading){
            mBaseView.showLoadingView();
        }
        mAsyncWeiboRunner.requestAsync(url, onPrepare(), method, mRequestListener);
    }
    public abstract WeiboParameters onPrepare();

    public abstract void onFinish(HttpResponse response);
}
