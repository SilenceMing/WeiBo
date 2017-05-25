package com.xiaoming.cweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.entities.UserEntity;
import com.xiaoming.cweibo.networks.BaseNetWork;
import com.xiaoming.cweibo.networks.Urls;
import com.xiaoming.cweibo.views.MVP_view.FansView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2418:58
 */

public class FansPresenterImp implements FansPresenter {

    private FansView mFansView;
    private String url;
    private int PAGE = 0;
    private WeiboParameters mParameters;

    public FansPresenterImp(FansView fansView) {
        mFansView = fansView;
        mParameters = new WeiboParameters(Constant.APP_KEY);
        url = Urls.ATTENTIONS;
    }

    @Override
    public void loadData(boolean showLoading) {
        PAGE = 0;
        LoadFriendsData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        PAGE ++ ;
        LoadFriendsData(true,showLoading);
    }

    @Override
    public void requestMyFans() {
        url = Urls.FANS;
    }

    @Override
    public void requestMyAttation() {
        url = Urls.ATTENTIONS;
    }

    private void LoadFriendsData(final boolean isLoadMore, boolean showLoading) {
        new BaseNetWork(mFansView,url,showLoading){
            @Override
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mFansView.getActivity()).getToken());
                mParameters.put(ParameterKey.UID, AccessTokenKeeper.readAccessToken(mFansView.getActivity()).getUid());
//                mParameters.put(ParameterKey.CURSOR,PAGE);
                mParameters.put(ParameterKey.COMMENT,10);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                Type type = new TypeToken<List<UserEntity>>(){}.getType();
                List<UserEntity> list = new Gson().fromJson(response.response,type);
                mFansView.onRefreshComplete(list,isLoadMore);
            }
        }.get();
    }
}
