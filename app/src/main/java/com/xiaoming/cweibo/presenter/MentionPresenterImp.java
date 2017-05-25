package com.xiaoming.cweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.entities.StatusEntity;
import com.xiaoming.cweibo.networks.BaseNetWork;
import com.xiaoming.cweibo.networks.Urls;
import com.xiaoming.cweibo.views.MVP_view.HomeView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2412:39
 */

public class MentionPresenterImp implements BasePresenter {

    private HomeView mHomeView;
    private WeiboParameters mParameters;
    private int PAGE = 1;

    public MentionPresenterImp(HomeView homeView) {
        mHomeView = homeView;
        mParameters = new WeiboParameters(Constant.APP_KEY);
    }

    @Override
    public void loadData(boolean showLoading) {
        PAGE = 1;
        LoadMentionData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        PAGE ++;
        LoadMentionData(true,showLoading);
    }

    private void LoadMentionData(final boolean loadMore,final boolean showLoading){
        new BaseNetWork(mHomeView, Urls.MENTIONS,showLoading) {
            @Override
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mHomeView.getActivity()).getToken());
                mParameters.put(ParameterKey.PAGE,PAGE);
                mParameters.put(ParameterKey.COUNT,10);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                List<StatusEntity> list = new ArrayList<>();
                Type type= new TypeToken<ArrayList<StatusEntity>>(){}.getType();
                list = new Gson().fromJson(response.response,type);
                mHomeView.onRefreshComplete(list,loadMore);
            }
        }.get();
    }
}
