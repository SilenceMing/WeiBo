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
 * @des 关注微博主页面实现类
 * @time 2017/5/2215:53
 */

public class HomePresenterImp implements HomePresenter {

    private int PAGE = 1;
    private String loadUrl = Urls.PUBLIC_TIMELINE;
    private List<StatusEntity> mEntityList;
    WeiboParameters mWeiboParameters;
    private HomeView mHomeView;


    public HomePresenterImp(HomeView homeView) {
        mHomeView = homeView;
        mWeiboParameters = new WeiboParameters(Constant.APP_KEY);
        mEntityList = new ArrayList<>();

    }

    @Override
    public void loadData(boolean showLoading) {
        PAGE = 1;
        LoadWbData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        PAGE ++ ;
        LoadWbData(true,showLoading);
    }

    @Override
    public void requestPublicTimeLine() {
        loadUrl = Urls.PUBLIC_TIMELINE;
        loadData(true);
    }

    @Override
    public void requestUserTimeLine() {
        loadUrl = Urls.USER_TIMELINE;
        loadData(true);
    }


    private void LoadWbData(final boolean loadMore,final boolean showLoading) {
        new BaseNetWork(mHomeView, loadUrl,showLoading) {
            @Override
            public WeiboParameters onPrepare() {
                mWeiboParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mHomeView.getActivity()).getToken());
                mWeiboParameters.put(ParameterKey.PAGE,PAGE);
                mWeiboParameters.put(ParameterKey.COUNT,10);
                return mWeiboParameters;
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
